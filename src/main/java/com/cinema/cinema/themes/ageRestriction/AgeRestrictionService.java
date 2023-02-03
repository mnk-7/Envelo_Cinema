package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private AgeRestrictionRepository ageRestrictionRepository;
    private DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public List<AgeRestrictionDtoRead> getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = ageRestrictionRepository.findAll(Sort.by("minAge"));
        return ageRestrictions.stream()
                .map(ageRestriction -> mapperService.mapToAgeRestrictionDto(ageRestriction))
                .toList();
    }

    @Transactional(readOnly = true)
    public AgeRestrictionDtoRead getAgeRestrictionById(long id) {
        AgeRestriction ageRestriction = validateAgeRestrictionExists(id);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public AgeRestrictionDtoRead addAgeRestriction(AgeRestrictionDtoWrite ageRestrictionDto) {
        //checkAgeRestrictionDataFormat(ageRestrictionDto);
        validateAgeRestrictionNotExists(ageRestrictionDto);
        AgeRestriction ageRestriction = mapperService.mapToAgeRestriction(ageRestrictionDto);
        ageRestrictionRepository.save(ageRestriction);
        return mapperService.mapToAgeRestrictionDto(ageRestriction);
    }

    @Transactional
    public void editAgeRestriction(long id, AgeRestrictionDtoWrite ageRestrictionDto) {
        AgeRestriction ageRestriction = validateAgeRestrictionExists(id);
        validateAgeRestrictionChanged(ageRestriction, ageRestrictionDto);
        //checkAgeRestrictionDataFormat(ageRestrictionDto);
        validateAgeRestrictionNotExists(ageRestrictionDto);
        ageRestriction.setMinAge(ageRestrictionDto.getMinAge());
        ageRestrictionRepository.save(ageRestriction);
    }

//    @Transactional
//    public void removeAgeRestriction(long id) {
//        validateAgeRestrictionExists(id);
//        ageRestrictionRepository.deleteById(id);
//    }

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

    private void validateAgeRestrictionChanged(AgeRestriction ageRestriction, AgeRestrictionDtoWrite ageRestrictionDto) {
        if (ageRestriction.getMinAge().equals(ageRestrictionDto.getMinAge())) {
            throw new ElementNotModifiedException("No change detected, age restriction with min age " + ageRestriction.getMinAge() + " has not been modified");
        }
    }

}
