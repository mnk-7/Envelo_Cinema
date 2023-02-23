package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
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

    private static final long id = 1L;
    private static final String minAge1 = "12";
    private static final String minAge2 = "15";

    @Test
    void validateExists() {
        AgeRestriction restriction = new AgeRestriction();
        restriction.setId(id);
        restriction.setMinAge(minAge2);

        Mockito.when(repository.findById(id)).thenReturn(Optional.of(restriction));
        try {
            AgeRestriction ageRestriction = validator.validateExists(id);
            assertNotNull(ageRestriction);
            assertEquals(ageRestriction.getId(), id);
        } catch (ElementNotFoundException ex) {
            fail("Exception thrown");
        }
    }

    @Test
    void validateExistsThrowEx() {
        Mockito.when(repository.findById(id)).thenReturn(Optional.empty());
        try {
            validator.validateExists(id);
            fail("Exception not thrown");
        } catch (ElementNotFoundException ex) {
            assertEquals("Age restriction with ID " + id + " not found", ex.getMessage());
        }
    }

    @Test
    void validateNotExists() {
        AgeRestriction restriction = new AgeRestriction();
        restriction.setId(id);
        restriction.setMinAge(minAge1);

        Mockito.when(repository.findByMinAge(restriction.getMinAge())).thenReturn(Optional.empty());
        try {
            validator.validateNotExists(restriction);
        } catch (ElementFoundException ex) {
            fail("Exception thrown");
        }
    }

    @Test
    void validateNotExistsThrowEx() {
        AgeRestriction restriction = new AgeRestriction();
        restriction.setId(id);
        restriction.setMinAge(minAge1);

        Mockito.when(repository.findByMinAge(restriction.getMinAge())).thenReturn(Optional.of(restriction));
        try {
            validator.validateNotExists(restriction);
            fail("Exception not thrown");
        } catch (ElementFoundException ex) {
            assertEquals("Age restriction with min age " + restriction.getMinAge() + " already exists", ex.getMessage());
        }
    }

    @Test
    void validateChanged() {
        AgeRestriction restriction1 = new AgeRestriction();
        restriction1.setId(id);
        restriction1.setMinAge(minAge1);

        AgeRestriction restriction2 = new AgeRestriction();
        restriction2.setId(id);
        restriction2.setMinAge(minAge1);

        try {
            validator.validateChanged(restriction1, restriction2);
            fail("Exception not thrown");
        } catch (ElementNotModifiedException ex) {
            assertEquals("No change detected, age restriction with min age " + restriction1.getMinAge() + " has not been modified", ex.getMessage());
        }
    }

    @Test
    void validateChangedThrowEx() {
        AgeRestriction restriction1 = new AgeRestriction();
        restriction1.setId(id);
        restriction1.setMinAge(minAge1);

        AgeRestriction restriction2 = new AgeRestriction();
        restriction2.setId(id);
        restriction2.setMinAge(minAge2);

        try {
            validator.validateChanged(restriction1, restriction2);
        } catch (ElementNotModifiedException ex) {
            fail("Exception thrown");
        }
    }

    @Test
    void validateInput() {
        //TODO
    }

}