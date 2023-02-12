package com.cinema.cinema.themes.venue.model;

import com.cinema.cinema.themes.seat.model.DoubleSeatOutputDto;
import com.cinema.cinema.themes.seat.model.SingleSeatOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VenueOutputDto {

    private Long id;
    private String name;
    private Integer rowsNumber;
    private Integer columnsNumber;
    Set<SingleSeatOutputDto> vipSeats = new HashSet<>();
    Set<DoubleSeatOutputDto> doubleSeats = new HashSet<>();

}
