package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.VenueException;
import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.DoubleSeat;
import com.cinema.cinema.models.seat.Seat;
import com.cinema.cinema.models.seat.SingleSeat;
import com.cinema.cinema.repositories.VenueRepository;
import com.cinema.cinema.services.seat.DoubleSeatService;
import com.cinema.cinema.services.seat.SingleSeatService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class VenueService {

    private VenueRepository venueRepository;
    private SingleSeatService singleSeatService;
    private DoubleSeatService doubleSeatService;

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
