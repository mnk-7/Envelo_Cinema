package com.cinema.cinema.themes.seat.repository;

import com.cinema.cinema.themes.seat.model.DoubleSeat;
import com.cinema.cinema.themes.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoubleSeatRepository extends JpaRepository<DoubleSeat, Long> {

    List<DoubleSeat> findAllByVenue(Venue venue);

}
