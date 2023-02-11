package com.cinema.cinema.themes.seat;

import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.seat.repository.SeatRepository;
import com.cinema.cinema.themes.venue.model.Venue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final SeatValidator seatValidator;

    @Transactional(readOnly = true)
    public Seat getSeat(long seatId) {
        return seatValidator.validateExists(seatId);
    }

    @Transactional(readOnly = true)
    public Seat getByVenueByRowByCol(Venue venue, int row, int column) {
        return seatValidator.validateExists(venue, row, column);
    }

    @Transactional(readOnly = true)
    public List<Seat> getAllSeats(Venue venue) {
        return seatRepository.findAllByVenue(venue);
    }

    @Transactional
    public void addSeats(Venue venue, Set<Seat> seats) {
        for (Seat seat : seats) {
            seat.setVenue(venue);
            seatValidator.validateInput(seat);
            seatRepository.save(seat);
        }
    }

}
