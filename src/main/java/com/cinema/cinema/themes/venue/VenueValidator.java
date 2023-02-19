package com.cinema.cinema.themes.venue;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.seat.model.SingleSeatInputDto;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VenueValidator extends ValidatorService<Venue> {

    private final VenueRepository venueRepository;

    public VenueValidator(Validator validator, VenueRepository venueRepository) {
        super(validator);
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue validateExists(long venueId) {
        Optional<Venue> venue = venueRepository.findById(venueId);
        if (venue.isEmpty()) {
            throw new ElementNotFoundException("Venue with ID " + venueId + " not found");
        }
        return venue.get();
    }

    public void validateIsActive(Venue venue) {
        if (!venue.isActive()) {
            throw new ProcessingException("You cannot modify or remove an inactive venue");
        }
    }

    public void validateVipSeatExists(int row, int column, Seat[][] seatsArray) {
        String message = "Seat you want to set as VIP (row: " + row + ", column: " + column + ") doesn't exist";
        validateSeatExists(row, column, seatsArray, message);
    }

    public void validateSeatsNumberForDouble(List<SingleSeatInputDto> seatsPair) {
        if (seatsPair.size() != 2) {
            throw new ArgumentNotValidException("You can combine together only two adjacent seats");
        }
    }

    public void validateSeatsForDouble(Seat[][] seatsArray, SingleSeatInputDto firstSeat, SingleSeatInputDto secondSeat) {
        validateSeatForDoubleExists(firstSeat.getRow(), firstSeat.getColumn(), seatsArray);
        validateSeatForDoubleExists(secondSeat.getRow(), secondSeat.getColumn(), seatsArray);
        validateSeatsForDoubleAreDifferent(firstSeat, secondSeat);
        validateSeatsForDoubleInSameRow(firstSeat, secondSeat);
        validateSeatsForDoubleAdjacent(firstSeat, secondSeat);
    }

    private void validateSeatForDoubleExists(int row, int column, Seat[][] seatsArray) {
        String message = "Seat you want to set as double seat (row: " + row + ", column: " + column + ") doesn't exist";
        validateSeatExists(row, column, seatsArray, message);
    }

    private void validateSeatExists(int row, int column, Seat[][] seatsArray, String message) {
        if (row < 0 || column < 0 || row >= seatsArray.length || column >= seatsArray[0].length) {
            throw new ArgumentNotValidException(message);
        }
    }

    private void validateSeatsForDoubleAreDifferent(SingleSeatInputDto firstSeat, SingleSeatInputDto secondSeat) {
        if (firstSeat.getRow().intValue() == secondSeat.getRow().intValue() && firstSeat.getColumn().intValue() == secondSeat.getColumn().intValue()) {
            throw new ArgumentNotValidException("You cannot combine together one seat");
        }
    }

    private void validateSeatsForDoubleInSameRow(SingleSeatInputDto firstSeat, SingleSeatInputDto secondSeat) {
        if (firstSeat.getRow().intValue() != secondSeat.getRow().intValue()) {
            throw new ArgumentNotValidException("You cannot combine together seats from different rows");
        }
    }

    private void validateSeatsForDoubleAdjacent(SingleSeatInputDto firstSeat, SingleSeatInputDto secondSeat) {
        if (Math.abs(firstSeat.getColumn() - secondSeat.getColumn()) != 1) {
            throw new ArgumentNotValidException("You cannot combine together two not adjacent seats");
        }
    }

    public void validateSeatsNotPartOfAnotherDouble(SingleSeat firstSeat, SingleSeat secondSeat, Set<Seat> singleSeats) {
        if (firstSeat.isPartOfCombinedSeat() || singleSeats.contains(firstSeat)) {
            validateSeatNotPartOfAnotherDouble(firstSeat);
        }
        if (secondSeat.isPartOfCombinedSeat() || singleSeats.contains(secondSeat)) {
            validateSeatNotPartOfAnotherDouble(secondSeat);
        }
    }

    private void validateSeatNotPartOfAnotherDouble(SingleSeat seat) {
        if (seat.isPartOfCombinedSeat()) {
            throw new ArgumentNotValidException("Seat (row: " + seat.getRow() + ", column: " + seat.getColumn() + ") is already part of another double seat");
        }
    }

}
