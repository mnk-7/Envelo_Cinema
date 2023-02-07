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

    private final AgeRestrictionRepository repository;

    public AgeRestrictionValidator(Validator validator, AgeRestrictionRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public AgeRestriction validateExists(long id) {
        Optional<AgeRestriction> ageRestriction = repository.findById(id);
        if (ageRestriction.isEmpty()) {
            throw new ElementNotFoundException("Age restriction with ID " + id + " not found");
        }
        return ageRestriction.get();
    }

    @Override
    public void validateNotExists(AgeRestriction ageRestrictionFromDto) {
        Optional<AgeRestriction> ageRestriction = repository.findByMinAge(ageRestrictionFromDto.getMinAge());
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
