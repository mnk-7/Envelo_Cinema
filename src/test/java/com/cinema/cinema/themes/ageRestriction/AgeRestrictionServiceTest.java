package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.isA;

@ExtendWith(MockitoExtension.class)
class AgeRestrictionServiceTest {

    @Mock
    private AgeRestrictionRepository repository;

    @Mock
    private AgeRestrictionValidator validator;

    @InjectMocks
    private AgeRestrictionService service;


    @Test
    void shouldReturnListOfAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = AgeRestrictionData.initializeListData();

        Mockito.when(repository.findAll(Sort.by(AgeRestrictionService.SORT_BY_FIELD))).thenReturn(ageRestrictions);

        List<AgeRestriction> restrictions = service.getAllAgeRestrictions();
        assertEquals(ageRestrictions.toString(), restrictions.toString());
    }

    @Test
    void shouldReturnEmptyListOfAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();

        Mockito.when(repository.findAll(Sort.by(AgeRestrictionService.SORT_BY_FIELD))).thenReturn(ageRestrictions);

        List<AgeRestriction> restrictions = service.getAllAgeRestrictions();
        assertEquals(ageRestrictions.toString(), restrictions.toString());
    }

    @Test
    void shouldReturnAgeRestrictionById() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(validator.validateExists(AgeRestrictionData.ID)).thenReturn(ageRestriction);

        AgeRestriction restriction = service.getAgeRestriction(AgeRestrictionData.ID);
        assertEquals(ageRestriction.toString(), restriction.toString());
    }

    @Test
    void shouldCreateNewAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.doNothing().when(validator).validateNotExists(isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateInput(isA(AgeRestriction.class));
        Mockito.when(repository.save(ageRestriction)).thenReturn(ageRestriction);

        AgeRestriction restriction = service.addAgeRestriction(ageRestriction);
        assertEquals(ageRestriction.toString(), restriction.toString());
    }

    @Test
    void shouldUpdateExistingAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestriction ageRestrictionEdited = AgeRestrictionData.initializeSingleData();
        ageRestrictionEdited.setMinAge(AgeRestrictionData.MIN_AGE_18);

        Mockito.when(validator.validateExists(AgeRestrictionData.ID)).thenReturn(ageRestriction);
        Mockito.doNothing().when(validator).validateInput(isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateChanged(isA(AgeRestriction.class), isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateNotExists(isA(AgeRestriction.class));
        Mockito.when(repository.save(ageRestriction)).thenReturn(ageRestriction);

        service.editAgeRestriction(AgeRestrictionData.ID, ageRestrictionEdited);
        assertEquals(ageRestriction.toString(), ageRestrictionEdited.toString());
        assertEquals(ageRestriction.getMinAge(), AgeRestrictionData.MIN_AGE_18);
    }

}
