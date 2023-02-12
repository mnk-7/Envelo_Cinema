package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private final AgeRestrictionRepository ageRestrictionRepository;
    private final AgeRestrictionValidator ageRestrictionValidator;
    private final DtoMapperService mapperService;


    @Transactional(readOnly = true)
    public List<AgeRestrictionOutputDto> getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = ageRestrictionRepository.findAll(Sort.by("minAge"));
        return ageRestrictions.stream()
                .map(mapperService::mapToAgeRestrictionDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgeRestrictionOutputDto getAgeRestriction(long ageRestrictionId) {
        AgeRestriction ageRestriction = ageRestrictionValidator.validateExists(ageRestrictionId);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public AgeRestrictionOutputDto addAgeRestriction(AgeRestrictionInputDto ageRestrictionDto) {
        AgeRestriction ageRestriction = mapperService.mapToAgeRestriction(ageRestrictionDto);
        ageRestrictionValidator.validateNotExists(ageRestriction);
        ageRestrictionValidator.validateInput(ageRestriction);
        ageRestriction = ageRestrictionRepository.save(ageRestriction);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public void editAgeRestriction(long ageRestrictionId, AgeRestrictionInputDto ageRestrictionDto) {
        AgeRestriction ageRestriction = ageRestrictionValidator.validateExists(ageRestrictionId);
        AgeRestriction ageRestrictionFromDto = mapperService.mapToAgeRestriction(ageRestrictionDto);
        ageRestrictionValidator.validateInput(ageRestrictionFromDto);
        ageRestrictionValidator.validateChanged(ageRestriction, ageRestrictionFromDto);
        ageRestrictionValidator.validateNotExists(ageRestrictionFromDto);
        setFields(ageRestriction, ageRestrictionFromDto);
        ageRestrictionRepository.save(ageRestriction);
    }

//    @Transactional
//    public void removeAgeRestriction(long ageRestrictionId) {
//        validateAgeRestrictionExists(ageRestrictionId);
//        ageRestrictionRepository.deleteById(ageRestrictionId);
//    }

    private void setFields(AgeRestriction ageRestriction, AgeRestriction ageRestrictionFromDto) {
        ageRestriction.setMinAge(ageRestrictionFromDto.getMinAge());
    }

}
