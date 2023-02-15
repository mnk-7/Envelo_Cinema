package com.cinema.cinema.themes.ticket;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.seat.SeatValidator;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.show.ShowService;
import com.cinema.cinema.themes.show.ShowValidator;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.ticket.model.TicketInputDto;
import com.cinema.cinema.themes.ticket.model.TicketOutputDto;
import com.cinema.cinema.themes.ticketType.TicketTypeValidator;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final CartService cartService;
    private final ShowService showService;
    private final TicketValidator ticketValidator;
    private final TicketTypeValidator ticketTypeValidator;
    private final ShowValidator showValidator;
    private final SeatValidator seatValidator;
    private final StandardUserValidator userValidator;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public TicketOutputDto getTicket(long ticketId) {
        Ticket ticket = ticketValidator.validateExists(ticketId);
        return mapperService.mapToTicketDto(ticket);
    }

    @Transactional
    public TicketOutputDto addTicket(Long userId, TicketInputDto ticketDto) {
        Ticket ticketFromDto = mapperService.mapToTicket(ticketDto);
        ticketValidator.validateInput(ticketFromDto);
        TicketType ticketType = ticketTypeValidator.validateExists(ticketDto.getTicketTypeId());
        ticketTypeValidator.validateIsAvailable(ticketType);
        Show show = showValidator.validateExists(ticketDto.getShowId());
        Seat seat = seatValidator.validateExists(ticketDto.getSeatId());
        ticketValidator.validateSeat(seat, show);
        Ticket ticket = createTicket(ticketType, show, seat);
        ticket = ticketRepository.save(ticket);
        showService.addTicket(show, ticket);
//        TODO - cart
//        if (userId != null) {
//            StandardUser user = userValidator.validateExists(userId);
//            cartService.addTicket(user, ticket);
//        }
        return mapperService.mapToTicketDto(ticket);
    }

    private Ticket createTicket(TicketType ticketType, Show show, Seat seat) {
        Ticket ticket = new Ticket();
        ticket.setType(ticketType);
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setPaid(false);
        return ticket;
    }

    @Transactional
    public void editIsPaid(long ticketId) {
        Ticket ticket = ticketValidator.validateExists(ticketId);
        ticketValidator.validateNotPaid(ticket);
        ticket.setPaid(true);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void removeTicket(Long userId, long ticketId) {
        Ticket ticket = ticketValidator.validateExists(ticketId);
        ticketValidator.validateCanBeCancelled(ticket);
//        TODO - cart
//        if (userId != null) {
//            StandardUser user = userValidator.validateExists(userId);
//            cartService.removeTicket(user, ticket);
//        }
        showService.removeTicket(ticket.getShow(), ticket);
        ticketRepository.deleteById(ticketId);
    }

    //TODO - order
//    public void editOrder(Order order, Set<Ticket> tickets) {
//        for (Ticket ticket : tickets) {
//            ticket.setOrder(order);
//            ticketRepository.save(ticket);
//        }
//    }

}
