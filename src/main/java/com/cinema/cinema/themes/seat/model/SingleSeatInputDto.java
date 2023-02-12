package com.cinema.cinema.themes.seat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleSeatInputDto {

    private Integer row;
    private Integer column;
    private boolean isVip;

}
