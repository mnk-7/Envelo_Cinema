package com.cinema.cinema.repository;

import com.cinema.cinema.model.Venue;

import java.util.ArrayList;
import java.util.List;

//TODO
public class VenueRepository {

    public Venue findVenueById(long id) {
        return new Venue();
    }

    public List<Venue> findAllActiveVenues() {
        return new ArrayList<>();
    }

    public void create(Venue venue) {
    }

    public void update(long id, Venue venue) {
    }

}
