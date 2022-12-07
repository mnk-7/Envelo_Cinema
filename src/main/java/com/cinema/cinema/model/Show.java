package com.cinema.cinema.model;

import com.cinema.cinema.model.content.Content;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public class Show {

    private Long id;
    private Venue venue;
    private Content content;
    private LocalDate date;
    private LocalTime startTime;
    //private LocalTime endTime;
    private Integer breakAfter; //in minutes
    private Set<Ticket> tickets;

}
