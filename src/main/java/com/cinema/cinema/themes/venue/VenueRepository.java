package com.cinema.cinema.themes.venue;

import com.cinema.cinema.themes.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VenueRepository extends JpaRepository<Venue, Long> {

    List<Venue> findAllByIsActiveTrue();

}
