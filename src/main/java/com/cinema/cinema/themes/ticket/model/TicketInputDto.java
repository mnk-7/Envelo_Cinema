package com.cinema.cinema.themes.ticket.model;

import com.cinema.cinema.themes.seat.model.SeatIdDto;
import com.cinema.cinema.themes.show.model.ShowIdDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeIdDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketInputDto {

    private ShowIdDto show;
    private SeatIdDto seat;
    private TicketTypeIdDto ticketType;

}
