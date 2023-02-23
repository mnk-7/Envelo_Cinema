package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.utils.DtoMapperService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AgeRestrictionController.class)
//@AutoConfigureMockMvc(addFilters = false)
class AgeRestrictionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgeRestrictionService service;

    @MockBean
    private DtoMapperService dtoMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAgeRestrictions() throws Exception {
        List<AgeRestriction> ageRestrictions = initializeListData();
        List<AgeRestrictionOutputDto> ageRestrictionsDto = initializeListDataDto();

        Mockito.when(service.getAllAgeRestrictions()).thenReturn(ageRestrictions);
        Mockito.when(dtoMapper.mapToAgeRestrictionDtoList(ageRestrictions)).thenReturn(ageRestrictionsDto);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v0.0/age-restrictions")
                        .content(objectMapper.writeValueAsString(ageRestrictionsDto)))
                .andReturn().getResponse();

        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(ageRestrictionsDto));
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void getAllAgeRestrictionsListEmpty() throws Exception {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();
        List<AgeRestrictionOutputDto> ageRestrictionsDto = new ArrayList<>();

        Mockito.when(service.getAllAgeRestrictions()).thenReturn(ageRestrictions);
        Mockito.when(dtoMapper.mapToAgeRestrictionDtoList(ageRestrictions)).thenReturn(ageRestrictionsDto);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v0.0/age-restrictions")
                        .content(objectMapper.writeValueAsString(ageRestrictionsDto)))
                .andReturn().getResponse();

        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(ageRestrictionsDto));
        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }

    @Test
    void getAgeRestriction() throws Exception {
        AgeRestriction ageRestriction = initializeSingleData();
        AgeRestrictionOutputDto ageRestrictionDto = initializeSingleDataDtoOutput();

        Mockito.when(service.getAgeRestriction(1L)).thenReturn(ageRestriction);
        Mockito.when(dtoMapper.mapToAgeRestrictionDto(ageRestriction)).thenReturn(ageRestrictionDto);

        MockHttpServletResponse response = mockMvc.perform(get("/api/v0.0/age-restrictions/1")
                        .content(objectMapper.writeValueAsString(ageRestrictionDto)))
                .andReturn().getResponse();

        assertEquals(response.getContentAsString(), objectMapper.writeValueAsString(ageRestrictionDto));
        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void addAgeRestriction() throws Exception {
        AgeRestriction ageRestriction = initializeSingleData();
        AgeRestrictionInputDto ageRestrictionDto = initializeSingleDataDtoInput();

        Mockito.when(dtoMapper.mapToAgeRestriction(ageRestrictionDto)).thenReturn(ageRestriction);
        Mockito.when(service.addAgeRestriction(Mockito.any(AgeRestriction.class))).thenReturn(ageRestriction);

        MockHttpServletResponse response = mockMvc.perform(post("/api/v0.0/age-restrictions")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ageRestriction))
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/api/v0.0/age-restrictions/1", response.getHeader(HttpHeaders.LOCATION));
    }

    @Test
    void editAgeRestriction() throws Exception {
        AgeRestriction ageRestriction = initializeSingleData();
        AgeRestrictionInputDto ageRestrictionDto = initializeSingleDataDtoInput();

        Mockito.when(dtoMapper.mapToAgeRestriction(ageRestrictionDto)).thenReturn(ageRestriction);
        Mockito.doNothing().when(service).editAgeRestriction(isA(Long.class), isA(AgeRestriction.class));

        MockHttpServletResponse response = mockMvc.perform(put("/api/v0.0/age-restrictions/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(ageRestriction))
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    private AgeRestriction initializeSingleData() {
        AgeRestriction restriction = new AgeRestriction();
        restriction.setId(1L);
        restriction.setMinAge("all");
        return restriction;
    }

    private AgeRestrictionOutputDto initializeSingleDataDtoOutput() {
        AgeRestrictionOutputDto restriction = new AgeRestrictionOutputDto();
        restriction.setId(1L);
        restriction.setMinAge("all");
        return restriction;
    }

    private AgeRestrictionInputDto initializeSingleDataDtoInput() {
        AgeRestrictionInputDto restriction = new AgeRestrictionInputDto();
        restriction.setMinAge("all");
        return restriction;
    }

    private List<AgeRestriction> initializeListData() {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();

        AgeRestriction first = new AgeRestriction();
        first.setMinAge("all");
        ageRestrictions.add(first);

        AgeRestriction second = new AgeRestriction();
        second.setMinAge("12");
        ageRestrictions.add(second);

        AgeRestriction third = new AgeRestriction();
        third.setMinAge("18");
        ageRestrictions.add(third);

        return ageRestrictions;
    }

    private List<AgeRestrictionOutputDto> initializeListDataDto() {
        List<AgeRestrictionOutputDto> ageRestrictions = new ArrayList<>();

        AgeRestrictionOutputDto first = new AgeRestrictionOutputDto();
        first.setId(1L);
        first.setMinAge("all");
        ageRestrictions.add(first);

        AgeRestrictionOutputDto second = new AgeRestrictionOutputDto();
        second.setId(2L);
        second.setMinAge("12");
        ageRestrictions.add(second);

        AgeRestrictionOutputDto third = new AgeRestrictionOutputDto();
        third.setId(3L);
        third.setMinAge("18");
        ageRestrictions.add(third);

        return ageRestrictions;
    }

}