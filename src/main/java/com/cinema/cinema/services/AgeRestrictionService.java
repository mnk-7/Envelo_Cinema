package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.AgeRestrictionException;
import com.cinema.cinema.models.categories.AgeRestriction;
import com.cinema.cinema.repositories.AgeRestrictionRepository;

import java.util.List;
import java.util.Optional;

public class AgeRestrictionService {

    private AgeRestrictionRepository ageRestrictionRepository;

    public AgeRestriction getAgeRestriction(long id){
        Optional<AgeRestriction> ageRestriction = ageRestrictionRepository.findAgeRestrictionById(id);
        if (ageRestriction.isEmpty()){
            throw new AgeRestrictionException("Age restriction with given ID not found");
        }
        return ageRestriction.get();
    }

    public List<AgeRestriction> getAllAgeRestrictions(){
        return ageRestrictionRepository.findAllAgeRestrictions();
    }

    public void addAgeRestriction(String minAge){
        AgeRestriction ageRestriction = new AgeRestriction();
        ageRestriction.setMinAge(minAge);
        ageRestrictionRepository.create(ageRestriction);
    }

    public void editAgeRestriction(long id, String minAge){
        AgeRestriction ageRestriction = getAgeRestriction(id);
        ageRestriction.setMinAge(minAge);
        ageRestrictionRepository.update(id, ageRestriction);
    }

}
