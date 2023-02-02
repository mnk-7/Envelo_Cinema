package com.cinema.cinema.repositories.seat;

import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.SingleSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SingleSeatRepository extends JpaRepository<SingleSeat, Long> {

    List<SingleSeat> findAllByVenue(Venue venue);

}
