package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AgeRestrictionValidator extends ValidatorService<AgeRestriction> {

    private final AgeRestrictionRepository ageRestrictionRepository;

    public AgeRestrictionValidator(Validator validator, AgeRestrictionRepository ageRestrictionRepository) {
        super(validator);
        this.ageRestrictionRepository = ageRestrictionRepository;
    }

    @Override
    public AgeRestriction validateExists(long ageRestrictionId) {
        Optional<AgeRestriction> ageRestriction = ageRestrictionRepository.findById(ageRestrictionId);
        if (ageRestriction.isEmpty()) {
            throw new ElementNotFoundException("Age restriction with ID " + ageRestrictionId + " not found");
        }
        return ageRestriction.get();
    }

    public void validateNotExists(AgeRestriction ageRestrictionFromDto) {
        Optional<AgeRestriction> ageRestriction = ageRestrictionRepository.findByMinAge(ageRestrictionFromDto.getMinAge());
        if (ageRestriction.isPresent()) {
            throw new ElementFoundException("Age restriction with min age " + ageRestriction.get().getMinAge() + " already exists");
        }
    }

    public void validateChanged(AgeRestriction ageRestriction, AgeRestriction ageRestrictionFromDto) {
        if (ageRestriction.equals(ageRestrictionFromDto)) {
            throw new ElementNotModifiedException("No change detected, age restriction with min age " + ageRestriction.getMinAge() + " has not been modified");
        }
    }

}
