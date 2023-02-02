package com.cinema.cinema.models.seat;

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
public class SingleSeat extends Seat {

    @Column(name = "seat_row")
    private int row;

    @Column(name = "seat_col")
    private int column;

}
