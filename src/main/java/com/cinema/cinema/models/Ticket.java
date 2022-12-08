package com.cinema.cinema.models;

import com.cinema.cinema.models.categories.TicketType;
import com.cinema.cinema.models.seat.Seat;

public class Ticket {

    private Long id;
    private Show show;
    private Seat seat;
    private TicketType type;
    private Boolean isPaid;

}
