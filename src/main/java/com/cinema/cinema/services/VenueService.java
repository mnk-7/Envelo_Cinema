package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.VenueException;
import com.cinema.cinema.models.Venue;
import com.cinema.cinema.models.seat.Seat;
import com.cinema.cinema.repositories.VenueRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class VenueService {

    private VenueRepository venueRepository;

    public Venue getVenue(long id) {
        Optional<Venue> venue = venueRepository.findVenueById(id);
        if (venue.isEmpty()){
            throw new VenueException("Venue with given ID not found");
        }
        return venue.get();
    }

    public List<Venue> getAllVenues() {
        return venueRepository.findAllActiveVenues();
    }

    public void addVenue(String name, int rows, int columns, Set<Seat> seats) {
        Venue venue = new Venue();
        venue.setName(name);
        venue.setRowsNumber(rows);
        venue.setColumnsNumber(columns);
        venue.setSeats(seats);
        venue.setIsActive(true);
        venueRepository.create(venue);
    }

    public Venue editVenueName(long id, String name) {
        Venue venue = getVenue(id);
        venue.setName(name);
        venueRepository.update(id, venue);
        return venue;
    }

    public Venue editVenueStructure(long id, int rows, int columns, Set<Seat> seats) {
        Venue editedVenue = getVenue(id);
        Venue newVenue = new Venue();
        newVenue.setName(editedVenue.getName());
        newVenue.setRowsNumber(rows);
        newVenue.setColumnsNumber(columns);
        newVenue.setSeats(seats);
        newVenue.setIsActive(true);
        editedVenue.setIsActive(false);
        venueRepository.create(newVenue);
        venueRepository.update(editedVenue.getId(), editedVenue);
        return newVenue;
    }

}
