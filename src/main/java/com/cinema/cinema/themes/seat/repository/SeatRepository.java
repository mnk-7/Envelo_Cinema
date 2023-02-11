package com.cinema.cinema.themes.seat.repository;

import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findAllByVenue(Venue venue);

}
