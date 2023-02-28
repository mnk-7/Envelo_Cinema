package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.PropertiesConfig;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@EnableConfigurationProperties(PropertiesConfig.class)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
public class AgeRestrictionControllerTestInt {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AgeRestrictionRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PropertiesConfig propertiesConfig;

    private String path;
    private String fullPath;
    private int idCounter = 0;


    @BeforeAll
    void init() {
        String endpoint = "age-restrictions";
        path = propertiesConfig.getPath(endpoint);
        fullPath = propertiesConfig.getFullPath(endpoint);
    }

    List<AgeRestriction> initializeDatabase() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        for (AgeRestriction ageRestriction : ageRestrictions) {
            repository.save(ageRestriction);
            idCounter++;
        }
        return ageRestrictions;
    }

    @AfterEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldReturnListOfAllAgeRestrictions() throws Exception {
        initializeDatabase();

        List<AgeRestrictionOutputDto> ageRestrictionsDto = AgeRestrictionData.initializeListOutputDto();
        ageRestrictionsDto.sort(Comparator.comparing(AgeRestrictionOutputDto::getMinAge));

        MockHttpServletResponse response = mockMvc.perform(get(path))
                .andReturn().getResponse();

        assertEquals(objectMapper.writeValueAsString(ageRestrictionsDto), response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldReturnEmptyListOfAllAgeRestrictions() throws Exception {
        List<AgeRestrictionOutputDto> ageRestrictionsDto = new ArrayList<>();

        MockHttpServletResponse response = mockMvc.perform(get(path))
                .andReturn().getResponse();

        assertEquals(objectMapper.writeValueAsString(ageRestrictionsDto), response.getContentAsString());
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void shouldReturnAgeRestrictionById() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionOutputDto ageRestrictionDto = AgeRestrictionData.initializeSingleOutputDto();
        long id = idCounter - ageRestrictions.size() + 1;
        ageRestrictionDto.setId(id);

        MockHttpServletResponse response = mockMvc.perform(get(path + "/" + id))
                .andReturn().getResponse();

        assertEquals(objectMapper.writeValueAsString(ageRestrictionDto), response.getContentAsString());
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenGettingAgeRestrictionByNotExistingId() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        mockMvc.perform(get(path + "/" + (ageRestrictions.size() + 1)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Age restriction with ID " + (ageRestrictions.size() + 1) + " not found")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of())));
    }

    @Test
    void shouldCreateNewAgeRestriction() throws Exception {
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();

        MockHttpServletResponse response = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        idCounter++;

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(fullPath + "/" + idCounter, response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNewAgeRestrictionWithMinAgeExisting() throws Exception {
        initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();

        mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Age restriction with min age " + ageRestrictionDto.getMinAge() + " already exists")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of())));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNewAgeRestrictionWithMinAgeNull() throws Exception {
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge(null);

        mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field is mandatory"))));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNewAgeRestrictionWithMinAgeBlank() throws Exception {
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("  ");

        mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot be empty or blank"))));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNewAgeRestrictionWithMinAgeEmpty() throws Exception {
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("");

        mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot be empty or blank"))));
    }

    @Test
    void shouldThrowExceptionWhenCreatingNewAgeRestrictionWithMinAgeTooLong() throws Exception {
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("123456789012345678901");

        mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot contain more than " + 20 + " characters"))));
    }

    @Test
    void shouldUpdateExistingAgeRestriction() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge(AgeRestrictionData.MIN_AGE_6);

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingNotExistingAgeRestriction() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge(AgeRestrictionData.MIN_AGE_6);

        mockMvc.perform(put(path + "/" + (ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Age restriction with ID " + (ageRestrictions.size() + 1) + " not found")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of())));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeNull() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge(null);

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field is mandatory"))));
    }


    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeBlank() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("  ");

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot be empty or blank"))));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeEmpty() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("");

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ageRestrictionDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot be empty or blank"))));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeTooLong() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge("123456789012345678901");

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Not valid data provided")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of("minAge", "Field cannot contain more than " + 20 + " characters"))));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeNotChanged() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("No change detected, age restriction with min age " + ageRestrictionDto.getMinAge() + " has not been modified")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of())));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingAgeRestrictionWithMinAgeExisting() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeDatabase();

        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();
        ageRestrictionDto.setMinAge(AgeRestrictionData.MIN_AGE_12);

        mockMvc.perform(put(path + "/" + (idCounter - ageRestrictions.size() + 1))
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestrictionDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.details", Matchers.is("Age restriction with min age " + ageRestrictionDto.getMinAge() + " already exists")))
                .andExpect(jsonPath("$.validationErrors", Matchers.is(Map.of())));
    }

}
