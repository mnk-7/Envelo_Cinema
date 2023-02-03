package com.cinema.cinema.themes.seat.model;

import com.cinema.cinema.themes.venue.model.Venue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seats")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean isVip;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

}
