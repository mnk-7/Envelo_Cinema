package com.cinema.cinema.themes.venue;

import com.cinema.cinema.themes.seat.SeatService;
import com.cinema.cinema.themes.seat.model.DoubleSeat;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.seat.model.SingleSeatInputDto;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.seat.SeatValidator;
import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.themes.venue.model.VenueInputDto;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final VenueValidator venueValidator;
    private final SeatService seatService;
    private final SeatValidator seatValidator;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public Venue getVenue(long venueId) {
        return venueValidator.validateExists(venueId);
    }

    @Transactional(readOnly = true)
    public List<Venue> getAllVenues() {
        return venueRepository.findAllByIsActiveTrue();
    }

    @Transactional
    public Venue addVenue(VenueInputDto venueDto) {
        Venue venueFromDto = mapperService.mapToVenue(venueDto);
        venueValidator.validateInput(venueFromDto);
        Venue venue = createVenue(venueFromDto);
        Seat[][] seatsArray = createSeatsArray(venueDto);
        Set<Seat> singleSeats = createSingleSeats(seatsArray, venueDto);
        venue = venueRepository.save(venue);
        seatService.addSeats(venue, singleSeats);
        Set<Seat> doubleSeats = createDoubleSeats(seatsArray, venueDto, venue);
        seatService.addSeats(venue, doubleSeats);
        venue.getSeats().addAll(singleSeats);
        venue.getSeats().addAll(doubleSeats);
        return venue;
    }

    private Venue createVenue(Venue venueFromDto) {
        Venue venue = new Venue();
        venue.setName(venueFromDto.getName());
        venue.setRowsNumber(venueFromDto.getRowsNumber());
        venue.setColumnsNumber(venueFromDto.getColumnsNumber());
        venue.setActive(true);
        return venue;
    }

    private Set<Seat> createSingleSeats(Seat[][] seatsArray, VenueInputDto venueDto) {
        Set<SingleSeatInputDto> vipSeatsDto = venueDto.getVipSeats();
        if (vipSeatsDto != null && !vipSeatsDto.isEmpty()) {
            updateVipSeats(seatsArray, vipSeatsDto);
        }
        return convertSeatsArray(seatsArray);
    }

    private Set<Seat> createDoubleSeats(Seat[][] seatsArray, VenueInputDto venueDto, Venue venue) {
        Set<List<SingleSeatInputDto>> doubleSeatsDto = venueDto.getDoubleSeats();
        if (doubleSeatsDto != null && !doubleSeatsDto.isEmpty()) {
            return updateToDoubleSeats(seatsArray, venueDto.getDoubleSeats(), venue);
        }
        return new HashSet<>();
    }

    private Seat[][] createSeatsArray(VenueInputDto venueDto) {
        int rows = venueDto.getRowsNumber() + 1;
        int columns = venueDto.getColumnsNumber() + 1;
        Seat[][] seats = new Seat[rows][columns];

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < columns; j++) {
                seats[i][j] = createSingleSeat(i, j);
            }
        }
        return seats;
    }

    private Seat createSingleSeat(int row, int column) {
        SingleSeat seat = new SingleSeat();
        seat.setRow(row);
        seat.setColumn(column);
        seat.setVip(false);
        seatValidator.validateInput(seat);
        return seat;
    }

    private void updateVipSeats(Seat[][] seatsArray, Set<SingleSeatInputDto> seatsFromDto) {
        for (SingleSeatInputDto seat : seatsFromDto) {
            int tempRow = seat.getRow();
            int tempCol = seat.getColumn();
            venueValidator.validateVipSeatExists(tempRow, tempCol, seatsArray);
            seatsArray[tempRow][tempCol].setVip(true);
        }
    }

    private Set<Seat> convertSeatsArray(Seat[][] seatsArray) {
        Set<Seat> seats = new HashSet<>();
        for (int i = 1; i < seatsArray.length; i++) {
            for (int j = 1; j < seatsArray[0].length; j++) {
                seats.add(seatsArray[i][j]);
            }
        }
        return seats;
    }

    private Set<Seat> updateToDoubleSeats(Seat[][] seatsArray, Set<List<SingleSeatInputDto>> seatsToCombine, Venue venue) {
        Set<Seat> newSeats = new HashSet<>();
        Set<Seat> seatsToUpdate = new HashSet<>();
        for (List<SingleSeatInputDto> seatsPair : seatsToCombine) {
            venueValidator.validateSeatsNumberForDouble(seatsPair);
            SingleSeatInputDto firstSeat = seatsPair.get(0);
            SingleSeatInputDto secondSeat = seatsPair.get(1);
            venueValidator.validateSeatsForDouble(seatsArray, firstSeat, secondSeat);
            SingleSeat first = (SingleSeat) seatService.getByVenueByRowByCol(venue, firstSeat.getRow(), firstSeat.getColumn());
            SingleSeat second = (SingleSeat) seatService.getByVenueByRowByCol(venue, secondSeat.getRow(), secondSeat.getColumn());
            venueValidator.validateSeatsNotPartOfAnotherDouble(first, second, seatsToUpdate);
            Seat doubleSeat = createDoubleSeat(first, second);
            newSeats.add(doubleSeat);
            seatsToUpdate.add(first);
            seatsToUpdate.add(second);
        }
        newSeats.addAll(seatsToUpdate);
        return newSeats;
    }

    private Seat createDoubleSeat(SingleSeat firstSeat, SingleSeat secondSeat) {
        DoubleSeat doubleSeat = new DoubleSeat();
        SingleSeat left;
        SingleSeat right;

        if (firstSeat.getColumn() < secondSeat.getColumn()) {
            left = firstSeat;
            right = secondSeat;
        } else {
            left = secondSeat;
            right = firstSeat;
        }

        setFieldsForDoubleSeat(doubleSeat, left, right);
        seatValidator.validateInput(doubleSeat);
        return doubleSeat;
    }

    private void setFieldsForDoubleSeat(DoubleSeat doubleSeat, SingleSeat left, SingleSeat right) {
        doubleSeat.setVip(left.isVip() || right.isVip());
        doubleSeat.setLeft(left);
        doubleSeat.setRight(right);
        left.setPartOfCombinedSeat(true);
        left.setPartOfCombinedSeat(true);
    }

    @Transactional
    public void editVenueName(long id, VenueInputDto venueDto) {
        Venue venue = getVenue(id);
        venueValidator.validateIsActive(venue);
        Venue venueFromDto = mapperService.mapToVenue(venueDto);
        venueValidator.validateInput(venueFromDto);
        venue.setName(venueDto.getName());
        venueRepository.save(venue);
    }

    @Transactional
    public void editVenueStructure(long id, VenueInputDto venueDto) {
        removeVenue(id);
        //TODO dodać walidację, by nie można było edytować nieaktywnej venue
        addVenue(venueDto);
    }

    @Transactional
    public void removeVenue(long id) {
        Venue venue = getVenue(id);
        venueValidator.validateIsActive(venue);
        venue.setActive(false);
        venueRepository.save(venue);
    }

}
