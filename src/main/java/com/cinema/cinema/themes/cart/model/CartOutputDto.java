package com.cinema.cinema.themes.cart.model;

import com.cinema.cinema.themes.ticket.model.TicketOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartOutputDto {

    private Long id;
    private Set<TicketOutputDto> tickets = new HashSet<>();
    private LocalDateTime lastUpdate;

}
