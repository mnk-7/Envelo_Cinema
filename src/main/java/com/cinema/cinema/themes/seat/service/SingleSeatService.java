package com.cinema.cinema.themes.seat.service;

import com.cinema.cinema.themes.seat.SeatException;
import com.cinema.cinema.themes.seat.repository.SingleSeatRepository;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.venue.Venue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class SingleSeatService extends SeatService<SingleSeat> {

    private SingleSeatRepository seatRepository;

    @Override
    public SingleSeat getSeat(long id) {
        Optional<SingleSeat> seat = seatRepository.findById(id);
        if (seat.isEmpty()) {
            throw new SeatException("Seat with given ID not found");
        }
        return seat.get();
    }

    @Override
    public List<SingleSeat> getAllSeats(Venue venue) {
        return seatRepository.findAllByVenue(venue);
    }

    @Override
    public Set<SingleSeat> addSeats(Venue venue, Set<SingleSeat> seats) {
        for (SingleSeat seat : seats) {
            seat.setVenue(venue);
            seatRepository.save(seat);
        }
        return seats;
    }

}
