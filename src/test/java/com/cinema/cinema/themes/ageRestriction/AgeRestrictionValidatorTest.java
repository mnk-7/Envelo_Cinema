package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.exceptions.Error;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgeRestrictionValidatorTest {

    @Mock
    private AgeRestrictionRepository repository;

    @InjectMocks
    private AgeRestrictionValidator validator;


    @Test
    void shouldNotThrowExceptionWhenValidateExists() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(repository.findById(AgeRestrictionData.ID)).thenReturn(Optional.of(ageRestriction));

        try {
            AgeRestriction ageRestrictionValidated = validator.validateExists(AgeRestrictionData.ID);
            assertNotNull(ageRestrictionValidated);
            assertEquals(ageRestrictionValidated.getId(), AgeRestrictionData.ID);
        } catch (ElementNotFoundException ex) {
            fail(Error.EX_THROWN);
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateExists() {
        Mockito.when(repository.findById(AgeRestrictionData.ID)).thenReturn(Optional.empty());

        try {
            validator.validateExists(AgeRestrictionData.ID);
            fail(Error.EX_NOT_THROWN);
        } catch (ElementNotFoundException ex) {
            assertEquals("Age restriction with ID " + AgeRestrictionData.ID + " not found", ex.getMessage());
        }
    }

    @Test
    void shouldNotThrowExceptionWhenValidateNotExists() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(repository.findByMinAge(ageRestriction.getMinAge())).thenReturn(Optional.empty());

        try {
            validator.validateNotExists(ageRestriction);
        } catch (ElementFoundException ex) {
            fail(Error.EX_THROWN);
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateNotExists() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(repository.findByMinAge(ageRestriction.getMinAge())).thenReturn(Optional.of(ageRestriction));

        try {
            validator.validateNotExists(ageRestriction);
            fail(Error.EX_NOT_THROWN);
        } catch (ElementFoundException ex) {
            assertEquals("Age restriction with min age " + ageRestriction.getMinAge() + " already exists", ex.getMessage());
        }
    }

    @Test
    void shouldNotThrowExceptionWhenValidateChanged() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestriction ageRestrictionEdited = AgeRestrictionData.initializeSingleData();
        ageRestrictionEdited.setMinAge(AgeRestrictionData.MIN_AGE_18);

        try {
            validator.validateChanged(ageRestriction, ageRestrictionEdited);
        } catch (ElementNotModifiedException ex) {
            fail(Error.EX_THROWN);
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateChanged() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestriction ageRestrictionEdited = AgeRestrictionData.initializeSingleData();

        try {
            validator.validateChanged(ageRestriction, ageRestrictionEdited);
            fail(Error.EX_NOT_THROWN);
        } catch (ElementNotModifiedException ex) {
            assertEquals("No change detected, age restriction with min age " + ageRestriction.getMinAge() + " has not been modified", ex.getMessage());
        }
    }

    @Test
    void validateInput() {
        //TODO
    }

}
