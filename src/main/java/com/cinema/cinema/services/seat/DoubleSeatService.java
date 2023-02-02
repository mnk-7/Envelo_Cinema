package com.cinema.cinema.services.seat;

import com.cinema.cinema.exceptions.SeatException;
import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.DoubleSeat;
import com.cinema.cinema.repositories.seat.DoubleSeatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class DoubleSeatService extends SeatService<DoubleSeat> {

    private DoubleSeatRepository seatRepository;

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
