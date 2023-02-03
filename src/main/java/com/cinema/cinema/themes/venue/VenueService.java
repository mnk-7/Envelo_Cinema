package com.cinema.cinema.themes.venue;

import com.cinema.cinema.themes.seat.model.DoubleSeat;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.seat.service.DoubleSeatService;
import com.cinema.cinema.themes.seat.service.SingleSeatService;
import com.cinema.cinema.themes.venue.model.Venue;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class VenueService {

    private final VenueRepository venueRepository;
    private final SingleSeatService singleSeatService;
    private final DoubleSeatService doubleSeatService;

    public Venue getVenue(long id) {
        Optional<Venue> venue = venueRepository.findById(id);
        if (venue.isEmpty()) {
            throw new VenueException("Venue with given ID not found");
        }
        return venue.get();
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAllByIsActiveTrue();
    }

    public void addVenue(String name, int rows, int columns, Set<Seat> seats) {
        Venue venue = new Venue();
        venue.setName(name);
        venue.setRowsNumber(rows);
        venue.setColumnsNumber(columns);
        venue.setSeats(seats);
        venue.setActive(true);
        venueRepository.save(venue);
    }

    public void editVenueName(long id, String name) {
        Venue venue = getVenue(id);
        venue.setName(name);
        venueRepository.save(venue);
    }

    public void editVenueStructure(long id, int rows, int columns, Set<SingleSeat> singleSeats, Set<DoubleSeat> doubleSeats) {
        Venue editedVenue = getVenue(id);
        Venue newVenue = new Venue();
        newVenue.setName(editedVenue.getName());
        newVenue.setRowsNumber(rows);
        newVenue.setColumnsNumber(columns);
        newVenue.setActive(true);
        editedVenue.setActive(false);
        venueRepository.save(editedVenue);
        Venue updatedVenue = venueRepository.save(newVenue);
        addSeatsStructure(updatedVenue, singleSeats, doubleSeats);
    }

    public void removeVenue(long id) {
        Venue venue = getVenue(id);
        venue.setActive(false);
        venueRepository.save(venue);
    }

    public void addSeatsStructure(Venue venue, Set<SingleSeat> singleSeats, Set<DoubleSeat> doubleSeats) {
        Set<SingleSeat> singleSeatSet = singleSeatService.addSeats(venue, singleSeats);
        Set<DoubleSeat> doubleSeatSet = doubleSeatService.addSeats(venue, doubleSeats);
        Set<Seat> seats = new HashSet<>();
        seats.addAll(singleSeatSet);
        seats.addAll(doubleSeatSet);
        venue.setSeats(seats);
        venueRepository.save(venue);
    }

}
