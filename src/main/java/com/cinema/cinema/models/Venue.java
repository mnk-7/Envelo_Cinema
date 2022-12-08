package com.cinema.cinema.models;

import com.cinema.cinema.models.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Venue { //created for each new screening hall configuration

    private Long id;
    private String name;
    private Integer rowsNumber;
    private Integer columnsNumber;
    private Set<Seat> seats;
    private Boolean isActive;

}
