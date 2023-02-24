package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.PropertiesConfig;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.utils.DtoMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@EnableConfigurationProperties(PropertiesConfig.class)
@TestPropertySource("classpath:application-test.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@WebMvcTest(AgeRestrictionController.class)
//@AutoConfigureMockMvc(addFilters = false)
class AgeRestrictionControllerTest {

    @MockBean
    private AgeRestrictionService service;

    @MockBean
    private DtoMapperService dtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropertiesConfig propertiesConfig;

    private String path;
    private String fullPath;


    @BeforeAll
    void init() {
        String endpoint = "age-restrictions";
        path = propertiesConfig.getPath(endpoint);
        fullPath = propertiesConfig.getFullPath(endpoint);
    }

    @Test
    void shouldReturnListOfAllAgeRestrictions() throws Exception {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();
        List<AgeRestrictionOutputDto> ageRestrictionsDto = AgeRestrictionData.initializeListOutputDto();

        Mockito.when(service.getAllAgeRestrictions()).thenReturn(ageRestrictions);
        Mockito.when(dtoMapper.mapToAgeRestrictionDtoList(ageRestrictions)).thenReturn(ageRestrictionsDto);

        String ageRestrictionsDtoJson = objectMapper.writeValueAsString(ageRestrictionsDto);

        MockHttpServletResponse response = mockMvc.perform(get(path)
                .content(ageRestrictionsDtoJson)).andReturn().getResponse();

        assertEquals(response.getContentAsString(), ageRestrictionsDtoJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldReturnEmptyListOfAllAgeRestrictions() throws Exception {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();
        List<AgeRestrictionOutputDto> ageRestrictionsDto = new ArrayList<>();

        Mockito.when(service.getAllAgeRestrictions()).thenReturn(ageRestrictions);
        Mockito.when(dtoMapper.mapToAgeRestrictionDtoList(ageRestrictions)).thenReturn(ageRestrictionsDto);

        String ageRestrictionsDtoJson = objectMapper.writeValueAsString(ageRestrictionsDto);

        MockHttpServletResponse response = mockMvc.perform(get(path)
                .content(ageRestrictionsDtoJson)).andReturn().getResponse();

        assertEquals(response.getContentAsString(), ageRestrictionsDtoJson);
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void shouldReturnAgeRestrictionById() throws Exception {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestrictionOutputDto ageRestrictionDto = AgeRestrictionData.initializeSingleOutputDto();

        Mockito.when(service.getAgeRestriction(AgeRestrictionData.ID)).thenReturn(ageRestriction);
        Mockito.when(dtoMapper.mapToAgeRestrictionDto(ageRestriction)).thenReturn(ageRestrictionDto);

        String ageRestrictionDtoJson = objectMapper.writeValueAsString(ageRestrictionDto);

        MockHttpServletResponse response = mockMvc.perform(get(path + "/" + AgeRestrictionData.ID)
                .content(ageRestrictionDtoJson)).andReturn().getResponse();

        assertEquals(response.getContentAsString(), ageRestrictionDtoJson);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldCreateNewAgeRestriction() throws Exception {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();

        Mockito.when(dtoMapper.mapToAgeRestriction(ageRestrictionDto)).thenReturn(ageRestriction);
        Mockito.when(service.addAgeRestriction(Mockito.any(AgeRestriction.class))).thenReturn(ageRestriction);

        MockHttpServletResponse response = mockMvc.perform(post(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestriction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals(fullPath + "/" + AgeRestrictionData.ID, response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    void shouldUpdateExistingAgeRestriction() throws Exception {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestrictionInputDto ageRestrictionDto = AgeRestrictionData.initializeSingleInputDto();

        Mockito.when(dtoMapper.mapToAgeRestriction(ageRestrictionDto)).thenReturn(ageRestriction);
        Mockito.doNothing().when(service).editAgeRestriction(isA(Long.class), isA(AgeRestriction.class));

        MockHttpServletResponse response = mockMvc.perform(put(path + "/" + AgeRestrictionData.ID)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestriction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

}
