package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private final AgeRestrictionRepository repository;
    private final AgeRestrictionValidator validator;
    private final DtoMapperService mapper;


    @Transactional(readOnly = true)
    public List<AgeRestrictionDtoRead> getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = repository.findAll(Sort.by("minAge"));
        return ageRestrictions.stream()
                .map(mapper::mapToAgeRestrictionDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgeRestrictionDtoRead getAgeRestrictionById(long id) {
        AgeRestriction ageRestriction = validator.validateExists(id);
        return mapper.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public AgeRestrictionDtoRead addAgeRestriction(AgeRestrictionDtoWrite ageRestrictionDto) {
        AgeRestriction ageRestriction = mapper.mapToAgeRestriction(ageRestrictionDto);
        validator.validateNotExists(ageRestriction);
        validator.validateInput(ageRestriction);
        ageRestriction = repository.save(ageRestriction);
        return mapper.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public void editAgeRestriction(long id, AgeRestrictionDtoWrite ageRestrictionDto) {
        AgeRestriction ageRestriction = validator.validateExists(id);
        AgeRestriction ageRestrictionFromDto = mapper.mapToAgeRestriction(ageRestrictionDto);
        validator.validateInput(ageRestrictionFromDto);
        validator.validateChanged(ageRestriction, ageRestrictionFromDto);
        validator.validateNotExists(ageRestrictionFromDto);
        ageRestrictionFromDto.setId(ageRestriction.getId());
        repository.save(ageRestrictionFromDto);
    }

//    @Transactional
//    public void removeAgeRestriction(long id) {
//        validateAgeRestrictionExists(id);
//        ageRestrictionRepository.deleteById(id);
//    }

}
