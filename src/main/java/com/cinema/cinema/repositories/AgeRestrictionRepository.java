package com.cinema.cinema.repositories;

import com.cinema.cinema.models.categories.AgeRestriction;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class AgeRestrictionRepository {

    public Optional<AgeRestriction> findAgeRestrictionById(long id) {
        return Optional.of(new AgeRestriction());
    }

    public List<AgeRestriction> findAllAgeRestrictions() {
        return new ArrayList<>();
    }

    public void create(AgeRestriction ageRestriction) {
    }

    public void update(long id, AgeRestriction ageRestriction) {
    }

}
