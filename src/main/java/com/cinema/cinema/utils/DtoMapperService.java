package com.cinema.cinema.utils;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class DtoMapperService {

    private final ModelMapper mapper;

    public AgeRestriction mapToAgeRestriction(AgeRestrictionDtoWrite ageRestrDto) {
        return mapper.map(ageRestrDto, AgeRestriction.class);
    }

    public AgeRestrictionDtoRead mapToAgeRestrictionDto(AgeRestriction ageRestr) {
        return mapper.map(ageRestr, AgeRestrictionDtoRead.class);
    }

}
