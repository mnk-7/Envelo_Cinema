package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.Error;
import com.cinema.cinema.exceptions.*;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AgeRestrictionValidatorTest {

    @Mock
    private AgeRestrictionRepository repository;

    @InjectMocks
    private AgeRestrictionValidator validatorAR;

    @Mock
    private Validator validator;


    @Test
    void shouldNotThrowExceptionWhenValidateExists() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(repository.findById(AgeRestrictionData.ID)).thenReturn(Optional.of(ageRestriction));

        try {
            AgeRestriction ageRestrictionValidated = validatorAR.validateExists(AgeRestrictionData.ID);
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
            validatorAR.validateExists(AgeRestrictionData.ID);
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
            validatorAR.validateNotExists(ageRestriction);
        } catch (ElementFoundException ex) {
            fail(Error.EX_THROWN);
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateNotExists() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();

        Mockito.when(repository.findByMinAge(ageRestriction.getMinAge())).thenReturn(Optional.of(ageRestriction));

        try {
            validatorAR.validateNotExists(ageRestriction);
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
            validatorAR.validateChanged(ageRestriction, ageRestrictionEdited);
        } catch (ElementNotModifiedException ex) {
            fail(Error.EX_THROWN);
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateChanged() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        AgeRestriction ageRestrictionEdited = AgeRestrictionData.initializeSingleData();

        try {
            validatorAR.validateChanged(ageRestriction, ageRestrictionEdited);
            fail(Error.EX_NOT_THROWN);
        } catch (ElementNotModifiedException ex) {
            assertEquals("No change detected, age restriction with min age " + ageRestriction.getMinAge() + " has not been modified", ex.getMessage());
        }
    }

    @Test
    void shouldNotThrowExceptionWhenValidateInput() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction.setMinAge("12345678901234567890");
        Set<ConstraintViolation<AgeRestriction>> violations = new HashSet<>();

        Mockito.when(validator.validate(ageRestriction)).thenReturn(violations);

        try {
            validatorAR.validateInput(ageRestriction);
            assertTrue(violations.isEmpty());
        } catch (ArgumentNotValidException ex) {
            fail();
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateInputMinAgeNull() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction.setMinAge(null);
        ConstraintViolation<AgeRestriction> minAgeNullViolation =
                AgeRestrictionData.initializeViolation("minAge", "Field is mandatory");
        Set<ConstraintViolation<AgeRestriction>> violations = Set.of(minAgeNullViolation);

        Mockito.when(validator.validate(ageRestriction)).thenReturn(violations);

        try {
            validatorAR.validateInput(ageRestriction);
            fail();
        } catch (ArgumentNotValidException ex) {
            assertEquals("Not valid data provided", ex.getMessage());
            Map<String, String> violationsEx = ex.getViolations();
            assertEquals(1, violationsEx.size());
            assertTrue(violationsEx.containsKey(minAgeNullViolation.getPropertyPath().toString()));
            assertTrue(violationsEx.containsValue(minAgeNullViolation.getMessage()));
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateInputMinAgeBlank() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction.setMinAge("  ");
        ConstraintViolation<AgeRestriction> minAgeBlankViolation =
                AgeRestrictionData.initializeViolation("minAge", "Field cannot be empty or blank");
        Set<ConstraintViolation<AgeRestriction>> violations = Set.of(minAgeBlankViolation);

        Mockito.when(validator.validate(ageRestriction)).thenReturn(violations);

        try {
            validatorAR.validateInput(ageRestriction);
            fail();
        } catch (ArgumentNotValidException ex) {
            assertEquals("Not valid data provided", ex.getMessage());
            Map<String, String> violationsEx = ex.getViolations();
            assertEquals(1, violationsEx.size());
            assertTrue(violationsEx.containsKey(minAgeBlankViolation.getPropertyPath().toString()));
            assertTrue(violationsEx.containsValue(minAgeBlankViolation.getMessage()));
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateInputMinAgeEmpty() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction.setMinAge("");
        ConstraintViolation<AgeRestriction> minAgeEmptyViolation =
                AgeRestrictionData.initializeViolation("minAge", "Field cannot be empty or blank");
        Set<ConstraintViolation<AgeRestriction>> violations = Set.of(minAgeEmptyViolation);

        Mockito.when(validator.validate(ageRestriction)).thenReturn(violations);

        try {
            validatorAR.validateInput(ageRestriction);
            fail();
        } catch (ArgumentNotValidException ex) {
            assertEquals("Not valid data provided", ex.getMessage());
            Map<String, String> violationsEx = ex.getViolations();
            assertEquals(1, violationsEx.size());
            assertTrue(violationsEx.containsKey(minAgeEmptyViolation.getPropertyPath().toString()));
            assertTrue(violationsEx.containsValue(minAgeEmptyViolation.getMessage()));
        }
    }

    @Test
    void shouldThrowExceptionWhenValidateInputMinAgeSizeTooLong() {
        AgeRestriction ageRestriction = AgeRestrictionData.initializeSingleData();
        ageRestriction.setMinAge("123456789012345678901");
        ConstraintViolation<AgeRestriction> minAgeTooLongViolation =
                AgeRestrictionData.initializeViolation("minAge", "Field cannot contain more than " + 20 + " characters");
        Set<ConstraintViolation<AgeRestriction>> violations = Set.of(minAgeTooLongViolation);

        Mockito.when(validator.validate(ageRestriction)).thenReturn(violations);

        try {
            validatorAR.validateInput(ageRestriction);
            fail();
        } catch (ArgumentNotValidException ex) {
            assertEquals("Not valid data provided", ex.getMessage());
            Map<String, String> violationsEx = ex.getViolations();
            assertEquals(1, violationsEx.size());
            assertTrue(violationsEx.containsKey(minAgeTooLongViolation.getPropertyPath().toString()));
            assertTrue(violationsEx.containsValue(minAgeTooLongViolation.getMessage()));
        }
    }

}
