package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class AgeRestrictionService {

    private final AgeRestrictionRepository ageRestrictionRepository;
    private final AgeRestrictionValidator ageRestrictionValidator;
    public static String SORT_BY_FIELD = "minAge";


    @Transactional(readOnly = true)
    public List<AgeRestriction> getAllAgeRestrictions() {
        return ageRestrictionRepository.findAll(Sort.by(SORT_BY_FIELD));
    }

    @Transactional(readOnly = true)
    public AgeRestriction getAgeRestriction(long ageRestrictionId) {
        return ageRestrictionValidator.validateExists(ageRestrictionId);
    }

    @Transactional
    public AgeRestriction addAgeRestriction(AgeRestriction ageRestriction) {
        ageRestrictionValidator.validateNotExists(ageRestriction);
        ageRestrictionValidator.validateInput(ageRestriction);
        return ageRestrictionRepository.save(ageRestriction);
    }

    @Transactional
    public void editAgeRestriction(long ageRestrictionId, AgeRestriction ageRestrictionFromDto) {
        AgeRestriction ageRestriction = ageRestrictionValidator.validateExists(ageRestrictionId);
        ageRestrictionValidator.validateInput(ageRestrictionFromDto);
        ageRestrictionValidator.validateChanged(ageRestriction, ageRestrictionFromDto);
        ageRestrictionValidator.validateNotExists(ageRestrictionFromDto);
        setFields(ageRestriction, ageRestrictionFromDto);
        ageRestrictionRepository.save(ageRestriction);
    }

    private void setFields(AgeRestriction ageRestriction, AgeRestriction ageRestrictionFromDto) {
        ageRestriction.setMinAge(ageRestrictionFromDto.getMinAge());
    }

}
