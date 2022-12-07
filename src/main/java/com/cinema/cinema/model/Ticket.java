package com.cinema.cinema.model;

import com.cinema.cinema.model.categories.TicketType;
import com.cinema.cinema.model.seat.Seat;

public class Ticket {

    private Long id;
    private Show show;
    private Seat seat;
    private TicketType type;
    private Boolean isPaid;

}
