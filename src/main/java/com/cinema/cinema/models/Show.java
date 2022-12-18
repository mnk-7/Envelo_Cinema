package com.cinema.cinema.models;

import com.cinema.cinema.models.content.Content;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
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
