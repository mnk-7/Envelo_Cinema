package com.cinema.cinema.themes.ticket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketInputDto {

    private Long showId;
    private Long seatId;
    private Long ticketTypeId;

}
