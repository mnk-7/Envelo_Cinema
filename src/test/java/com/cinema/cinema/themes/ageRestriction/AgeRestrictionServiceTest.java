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

import static org.junit.jupiter.api.Assertions.*;
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
    void getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = initializeListData();
        Mockito.when(repository.findAll(Sort.by("minAge"))).thenReturn(ageRestrictions);
        List<AgeRestriction> restrictions = service.getAllAgeRestrictions();
        assertEquals(ageRestrictions.toString(), restrictions.toString());
    }

    @Test
    void getAllAgeRestrictionsEmptyList() {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();
        Mockito.when(repository.findAll(Sort.by("minAge"))).thenReturn(ageRestrictions);
        List<AgeRestriction> restrictions = service.getAllAgeRestrictions();
        assertEquals(ageRestrictions.toString(), restrictions.toString());
    }

    @Test
    void getAgeRestriction() {
        AgeRestriction ageRestriction = initializeSingleData();
        Mockito.when(validator.validateExists(1L)).thenReturn(ageRestriction);
        AgeRestriction restriction = service.getAgeRestriction(1L);
        assertEquals(ageRestriction.toString(), restriction.toString());
    }

    @Test
    void addAgeRestriction() {
        AgeRestriction ageRestriction = initializeSingleData();
        Mockito.doNothing().when(validator).validateNotExists(isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateInput(isA(AgeRestriction.class));
        Mockito.when(repository.save(ageRestriction)).thenReturn(ageRestriction);
        AgeRestriction restriction = service.addAgeRestriction(ageRestriction);
        assertEquals(ageRestriction.toString(), restriction.toString());
    }

    @Test
    void editAgeRestriction() {
        AgeRestriction ageRestriction = initializeSingleData();
        AgeRestriction ageRestrictionEdited = initializeSingleData();
        ageRestrictionEdited.setMinAge("7");

        Mockito.when(validator.validateExists(1L)).thenReturn(ageRestriction);
        Mockito.doNothing().when(validator).validateInput(isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateChanged(isA(AgeRestriction.class), isA(AgeRestriction.class));
        Mockito.doNothing().when(validator).validateNotExists(isA(AgeRestriction.class));
        Mockito.when(repository.save(ageRestriction)).thenReturn(ageRestriction);

        service.editAgeRestriction(1L, ageRestrictionEdited);

        assertEquals(ageRestriction.toString(), ageRestrictionEdited.toString());
        assertEquals(ageRestriction.getMinAge(), "7");
    }

    private AgeRestriction initializeSingleData() {
        AgeRestriction restriction = new AgeRestriction();
        restriction.setId(1L);
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

}