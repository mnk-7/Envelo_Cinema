package com.cinema.cinema.themes.seat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleSeatOutputDto {

    private Long id;
    private boolean isVip;
    private Integer row;
    private Integer column;

}
