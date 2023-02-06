package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.utils.DtoMapperService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private final AgeRestrictionRepository ageRestrictionRepository;
    private final DtoMapperService mapperService;
    private final Validator validator;


    @Transactional(readOnly = true)
    public List<AgeRestrictionDtoRead> getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = ageRestrictionRepository.findAll(Sort.by("minAge"));
        return ageRestrictions.stream()
                .map(mapperService::mapToAgeRestrictionDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AgeRestrictionDtoRead getAgeRestrictionById(long id) {
        AgeRestriction ageRestriction = validateAgeRestrictionExists(id);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public AgeRestrictionDtoRead addAgeRestriction(AgeRestrictionDtoWrite ageRestrictionDto) {
        validateAgeRestrictionNotExists(ageRestrictionDto);
        AgeRestriction ageRestriction = mapperService.mapToAgeRestriction(ageRestrictionDto);
        validateInputForAgeRestriction(ageRestriction);
        ageRestriction = ageRestrictionRepository.save(ageRestriction);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public void editAgeRestriction(long id, AgeRestrictionDtoWrite ageRestrictionDto) {
        AgeRestriction ageRestriction = validateAgeRestrictionExists(id);
        AgeRestriction ageRestrictionFromDto = mapperService.mapToAgeRestriction(ageRestrictionDto);
        validateInputForAgeRestriction(ageRestrictionFromDto);
        validateAgeRestrictionChanged(ageRestriction, ageRestrictionFromDto);
        validateAgeRestrictionNotExists(ageRestrictionDto);
        ageRestrictionFromDto.setId(ageRestriction.getId());
        ageRestrictionRepository.save(ageRestrictionFromDto);
    }

//    @Transactional
//    public void removeAgeRestriction(long id) {
//        validateAgeRestrictionExists(id);
//        ageRestrictionRepository.deleteById(id);
//    }

    private void validateInputForAgeRestriction(AgeRestriction ageRestriction) {
        Set<ConstraintViolation<AgeRestriction>> violations = validator.validate(ageRestriction);
        if (!violations.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation<AgeRestriction> violation : violations) {
                messages.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ArgumentNotValidException("Not valid data provided", messages);
        }
    }

    private AgeRestriction validateAgeRestrictionExists(long id) {
        Optional<AgeRestriction> ageRestriction = ageRestrictionRepository.findById(id);
        if (ageRestriction.isEmpty()) {
            throw new ElementNotFoundException("Age restriction with ID " + id + " not found");
        }
        return ageRestriction.get();
    }

    private void validateAgeRestrictionNotExists(AgeRestrictionDtoWrite ageRestrictionDto) {
        Optional<AgeRestriction> ageRestriction = ageRestrictionRepository.findByMinAge(ageRestrictionDto.getMinAge());
        if (ageRestriction.isPresent()) {
            throw new ElementFoundException("Age restriction with min age " + ageRestriction.get().getMinAge() + " already exists");
        }
    }

    private void validateAgeRestrictionChanged(AgeRestriction ageRestriction, AgeRestriction ageRestrictionFromDto) {
        if (ageRestriction.equals(ageRestrictionFromDto)) {
            throw new ElementNotModifiedException("No change detected, age restriction with min age " + ageRestriction.getMinAge() + " has not been modified");
        }
    }

}
