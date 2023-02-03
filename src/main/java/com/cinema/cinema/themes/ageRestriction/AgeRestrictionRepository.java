package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AgeRestrictionRepository extends JpaRepository<AgeRestriction, Long> {

    Optional<AgeRestriction> findByMinAge(String minAge);

}
