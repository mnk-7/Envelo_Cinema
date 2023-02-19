package com.cinema.cinema.themes.seat;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.seat.repository.SeatRepository;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.seat.repository.SingleSeatRepository;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SeatValidator extends ValidatorService<Seat> {

    private final SeatRepository seatRepository;
    private final SingleSeatRepository singleSeatRepository;

    public SeatValidator(Validator validator, SeatRepository seatRepository, SingleSeatRepository singleSeatRepository) {
        super(validator);
        this.seatRepository = seatRepository;
        this.singleSeatRepository = singleSeatRepository;
    }

    @Override
    public Seat validateExists(long seatId) {
        Optional<Seat> seat = seatRepository.findById(seatId);
        if (seat.isEmpty()) {
            throw new ElementNotFoundException("Seat with ID " + seatId + " not found");
        }
        return seat.get();
    }

    public Seat validateExists(Venue venue, int row, int column) {
        Optional<SingleSeat> seat = singleSeatRepository.findByVenueAndRowAndColumn(venue, row, column).stream().findFirst();
        if (seat.isEmpty()) {
            throw new ElementNotFoundException("Seat not found");
        }
        return seat.get();
    }

}
