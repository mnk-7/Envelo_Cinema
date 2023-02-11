package com.cinema.cinema.themes.venue.model;

import com.cinema.cinema.themes.seat.model.SeatDtoWrite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VenueDtoWrite {

    private String name;
    private Integer rowsNumber;
    private Integer columnsNumber;
    Set<SeatDtoWrite> vipSeats;
    Set<List<SeatDtoWrite>> doubleSeats;

}
