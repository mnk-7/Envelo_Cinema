package com.cinema.cinema.repositories;

import com.cinema.cinema.models.Venue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
public class VenueRepository {

    public Optional<Venue> findVenueById(long id) {
        return Optional.of(new Venue());
    }

    public List<Venue> findAllActiveVenues() {
        return new ArrayList<>();
    }

    public void create(Venue venue) {
    }

    public void update(long id, Venue venue) {
    }

}
