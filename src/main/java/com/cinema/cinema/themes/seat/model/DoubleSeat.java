package com.cinema.cinema.themes.seat.model;

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
public class DoubleSeat extends Seat {

    @ManyToOne
    @JoinColumn(name = "left_seat_id")
    private SingleSeat left;

    @ManyToOne
    @JoinColumn(name = "right_seat_id")
    private SingleSeat right;

}
