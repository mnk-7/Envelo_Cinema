package com.cinema.cinema.themes.seat.service;

import com.cinema.cinema.themes.venue.Venue;

import java.util.List;
import java.util.Set;

//@Service - is it needed? //TODO
public abstract class SeatService<T> {
    //TODO - to be analyzed, if double and single services needed

    protected abstract T getSeat(long id);

    protected abstract List<T> getAllSeats(Venue venue);

    protected abstract Set<T> addSeats(Venue venue, Set<T> seat);

}
