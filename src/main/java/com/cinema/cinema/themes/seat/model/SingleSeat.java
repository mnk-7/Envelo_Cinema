package com.cinema.cinema.themes.seat.model;

import jakarta.validation.constraints.Min;
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
@DiscriminatorValue("single")
public class SingleSeat extends Seat {

    @NotNull(message = "Field is mandatory")
    @Min(value = 1, message = "Value cannot be less than {value}")
    @Column(name = "seat_row")
    private Integer row;

    @NotNull(message = "Field is mandatory")
    @Min(value = 1, message = "Value cannot be less than {value}")
    @Column(name = "seat_col")
    private Integer column;

    @Column(name = "seat_part")
    private boolean partOfCombinedSeat;

}
