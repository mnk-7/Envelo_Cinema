package com.cinema.cinema.themes.seat.model;

import jakarta.validation.constraints.NotNull;
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
@DiscriminatorValue("double")
public class DoubleSeat extends Seat {

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "left_seat_id")
    private SingleSeat left;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "right_seat_id")
    private SingleSeat right;

}
