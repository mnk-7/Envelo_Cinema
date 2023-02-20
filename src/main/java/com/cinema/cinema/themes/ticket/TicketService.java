package com.cinema.cinema.themes.ticket;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.cart.CartValidator;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.seat.SeatValidator;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.show.ShowService;
import com.cinema.cinema.themes.show.ShowValidator;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.ticketType.TicketTypeValidator;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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
    private final CartValidator cartValidator;

    @Transactional(readOnly = true)
    public Ticket getTicket(long ticketId) {
        return ticketValidator.validateExists(ticketId);
    }

    @Transactional
    public Ticket addTicket(Long userId, Ticket ticketFromDto) {
        ticketValidator.validateInput(ticketFromDto);
        TicketType ticketType = ticketTypeValidator.validateExists(ticketFromDto.getTicketType().getId());
        ticketTypeValidator.validateIsAvailable(ticketType);
        Show show = showValidator.validateExists(ticketFromDto.getShow().getId());
        showValidator.validateInFuture(show);
        Seat seat = seatValidator.validateExists(ticketFromDto.getSeat().getId());
        ticketValidator.validateSeat(seat, show);
        Ticket ticket = createTicket(ticketType, show, seat);
        ticket = ticketRepository.save(ticket);
        showService.addTicket(show, ticket);
        if (userId != null) {
            StandardUser user = userValidator.validateExists(userId);
            cartService.addTicket(user.getCart().getId(), ticket);
//        } else {
//            //TODO - koszyk niezalogowanego użytkownika
        }
        return ticket;
    }

    private Ticket createTicket(TicketType ticketType, Show show, Seat seat) {
        Ticket ticket = new Ticket();
        ticket.setTicketType(ticketType);
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setPaid(false);
        return ticket;
    }

    @Transactional
    public void editTicketType(long ticketId, Ticket ticketFromDto) {
        Ticket ticket = ticketValidator.validateExists(ticketId);
        ticketValidator.validateNotPaid(ticket);
        TicketType ticketType = ticketValidator.validateTicketTypeNotEmpty(ticketFromDto.getTicketType());
        ticketType = ticketTypeValidator.validateExists(ticketType.getId());
        ticketTypeValidator.validateIsAvailable(ticketType);
        ticket.setTicketType(ticketType);
        ticketRepository.save(ticket);
    }

    @Transactional
    public void removeTicketFromCart(Long userId, long ticketId) {
        Ticket ticket = ticketValidator.validateExists(ticketId);
        ticketValidator.validateNotPaid(ticket);
        if (userId != null) {
            StandardUser user = userValidator.validateExists(userId);
            cartValidator.validateTicketInCart(user.getCart(), ticket);
            cartService.removeTicket(user.getCart().getId(), ticket);
        } else {
            cartValidator.validateTicketNotInCart(ticket);
            //TODO - dodatkowy else dla koszyka niezalogowanego użytkownika
        }
        showService.removeTicket(ticket.getShow(), ticket);
        ticketRepository.deleteById(ticketId);
    }

    @Transactional
    public void addOrder(Order order, Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticket.setOrder(order);
            ticketRepository.save(ticket);
        }
    }

    @Transactional
    public void removeTicketFromOrder(Order order) {
        for (Ticket ticket : order.getTickets()) {
            ticket.setOrder(null);
            showService.removeTicket(ticket.getShow(), ticket);
            ticketRepository.deleteById(ticket.getId());
        }
    }

}
