package com.cinema.cinema.themes.ticketType.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketTypeDtoWrite {

    private String name;
    private String description;
    private BigDecimal price;
    private boolean isAvailable;

}
