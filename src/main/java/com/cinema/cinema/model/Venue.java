package com.cinema.cinema.model;

import com.cinema.cinema.model.seat.Seat;

import java.util.Set;

public class Venue { //created for each new screening hall configuration

    private Long id;
    private String name;
    private Integer rowsNumber;
    private Integer columnsNumber;
    private Set<Seat> seats;
    private Boolean isActive;

}
