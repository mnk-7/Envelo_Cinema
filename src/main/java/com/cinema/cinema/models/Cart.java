package com.cinema.cinema.models;

import java.time.LocalDateTime;
import java.util.Set;

public class Cart {

    private Long id;
    private Set<Ticket> tickets;
    private LocalDateTime lastUpdate;

}
