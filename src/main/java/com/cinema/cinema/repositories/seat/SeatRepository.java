package com.cinema.cinema.repositories.seat;

import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.DoubleSeat;
import com.cinema.cinema.models.seat.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<DoubleSeat> findAllByVenue(Venue venue);

}
