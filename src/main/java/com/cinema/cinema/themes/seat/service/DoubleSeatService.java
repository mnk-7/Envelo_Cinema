package com.cinema.cinema.themes.seat.service;

import com.cinema.cinema.themes.seat.repository.DoubleSeatRepository;
import com.cinema.cinema.themes.seat.SeatException;
import com.cinema.cinema.themes.seat.model.DoubleSeat;
import com.cinema.cinema.themes.venue.model.Venue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class DoubleSeatService extends SeatService<DoubleSeat> {

    private final DoubleSeatRepository seatRepository;

    @Override
    public DoubleSeat getSeat(long id) {
        Optional<DoubleSeat> seat = seatRepository.findById(id);
        if (seat.isEmpty()) {
            throw new SeatException("Seat with given ID not found");
        }
        return seat.get();
    }

    @Override
    public List<DoubleSeat> getAllSeats(Venue venue) {
        return seatRepository.findAllByVenue(venue);
    }

    @Override
    public Set<DoubleSeat> addSeats(Venue venue, Set<DoubleSeat> seats) {
        for (DoubleSeat seat : seats) {
            seat.setVenue(venue);
            seatRepository.save(seat);
        }
        return seats;
    }

}
