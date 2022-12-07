package com.cinema.cinema.repository;

import com.cinema.cinema.model.Venue;

import java.util.ArrayList;
import java.util.List;

//TODO
public class VenueRepository {

    public Venue findVenueById(Long id) {
        return new Venue();
    }

    public List<Venue> findAllActiveVenues() {
        List<Venue> list = new ArrayList<>();
        list.add(new Venue());
        return list;
    }

    public void create(Venue venue) {
    }

    ;

    public void update(Long id, Venue venue) {
    }

}
