package com.cinema.cinema.themes.seat.repository;

import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.venue.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleSeatRepository extends JpaRepository<SingleSeat, Long> {

    List<SingleSeat> findAllByVenue(Venue venue);

}
