package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import jakarta.validation.ConstraintViolation;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.util.ArrayList;
import java.util.List;

public class AgeRestrictionData {

    public static final long ID = 1L;
    public static final String MIN_AGE_ALL = "all";
    public static final String MIN_AGE_6 = "6";
    public static final String MIN_AGE_12 = "12";
    public static final String MIN_AGE_18 = "18";


    public static AgeRestriction initializeSingleData() {
        AgeRestriction ageRestriction = new AgeRestriction();
        ageRestriction.setId(ID);
        ageRestriction.setMinAge(MIN_AGE_ALL);
        return ageRestriction;
    }

    public static AgeRestrictionOutputDto initializeSingleOutputDto() {
        AgeRestrictionOutputDto ageRestrictionDto = new AgeRestrictionOutputDto();
        ageRestrictionDto.setId(ID);
        ageRestrictionDto.setMinAge(MIN_AGE_ALL);
        return ageRestrictionDto;
    }

    public static AgeRestrictionInputDto initializeSingleInputDto() {
        AgeRestrictionInputDto ageRestrictionDto = new AgeRestrictionInputDto();
        ageRestrictionDto.setMinAge(MIN_AGE_ALL);
        return ageRestrictionDto;
    }

    public static List<AgeRestriction> initializeListData() {
        List<AgeRestriction> ageRestrictions = new ArrayList<>();

        AgeRestriction first = new AgeRestriction();
        first.setMinAge(MIN_AGE_ALL);
        ageRestrictions.add(first);

        AgeRestriction second = new AgeRestriction();
        second.setMinAge(MIN_AGE_12);
        ageRestrictions.add(second);

        AgeRestriction third = new AgeRestriction();
        third.setMinAge(MIN_AGE_18);
        ageRestrictions.add(third);

        return ageRestrictions;
    }

    public static List<AgeRestrictionOutputDto> initializeListOutputDto() {
        List<AgeRestrictionOutputDto> ageRestrictionsDto = new ArrayList<>();

        AgeRestrictionOutputDto first = new AgeRestrictionOutputDto();
        first.setId(ID);
        first.setMinAge(MIN_AGE_ALL);
        ageRestrictionsDto.add(first);

        AgeRestrictionOutputDto second = new AgeRestrictionOutputDto();
        second.setId(ID + 1);
        second.setMinAge(MIN_AGE_12);
        ageRestrictionsDto.add(second);

        AgeRestrictionOutputDto third = new AgeRestrictionOutputDto();
        third.setId(ID + 2);
        third.setMinAge(MIN_AGE_18);
        ageRestrictionsDto.add(third);

        return ageRestrictionsDto;
    }

    public static ConstraintViolation<AgeRestriction> initializeViolation(String key, String value) {
        return ConstraintViolationImpl.forParameterValidation(null, null, null,
                value, null, null, null, null, PathImpl.createPathFromString(key),
                null, null, null);
    }

}
