package com.cinema.cinema.repositories.seat;

import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.DoubleSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoubleSeatRepository extends JpaRepository<DoubleSeat, Long> {

    List<DoubleSeat> findAllByVenue(Venue venue);

}
