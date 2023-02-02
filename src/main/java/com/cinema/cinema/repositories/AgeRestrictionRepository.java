package com.cinema.cinema.repositories;

import com.cinema.cinema.models.AgeRestriction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgeRestrictionRepository extends JpaRepository<AgeRestriction, Long> {

}
