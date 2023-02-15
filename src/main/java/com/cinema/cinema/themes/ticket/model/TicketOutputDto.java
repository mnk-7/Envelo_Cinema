package com.cinema.cinema.themes.ticket.model;

import com.cinema.cinema.themes.seat.model.DoubleSeatOutputDto;
import com.cinema.cinema.themes.seat.model.SingleSeatOutputDto;
import com.cinema.cinema.themes.show.model.ShowOutputShortDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TicketOutputDto {

    private Long id;
    private ShowOutputShortDto show;
    private SingleSeatOutputDto singleSeat;
    private DoubleSeatOutputDto doubleSeat;
    private TicketTypeOutputDto ticketType;
    private boolean isPaid;

}
