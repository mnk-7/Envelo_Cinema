package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.TicketException;
import com.cinema.cinema.models.Show;
import com.cinema.cinema.models.Ticket;
import com.cinema.cinema.models.TicketType;
import com.cinema.cinema.models.seat.SingleSeat;
import com.cinema.cinema.models.user.User;
import com.cinema.cinema.repositories.TicketRepository;
import com.cinema.cinema.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class TicketService {

    private TicketRepository ticketRepository;
    private CartService cartService;
    private ShowService showService;

    public Ticket getTicket(long id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (ticket.isEmpty()) {
            throw new TicketException("Ticket with given ID not found");
        }
        return ticket.get();
    }

    public void addTicket(User user, Show show, SingleSeat seat, TicketType ticketType) {
        Ticket ticket = new Ticket();
        ticket.setShow(show);
        ticket.setSeat(seat);
        ticket.setType(ticketType);
        ticket.setPaid(false);
        ticketRepository.save(ticket);
        showService.addTicket(show.getId(), ticket);
        if (user != null) {
            cartService.addTicket(user.getId(), ticket);
        }
    }

    public void editIsPaid(long id) {
        Ticket ticket = getTicket(id);
        ticket.setPaid(true);
        ticketRepository.save(ticket);
    }

    public void editOrder(Order order, Set<Ticket> tickets) {
        for (Ticket ticket : tickets) {
            ticket.setOrder(order);
            ticketRepository.save(ticket);
        }
    }

    public void removeTicket(long id, User user) {
        Ticket ticket = getTicket(id);
        if (ticket.isPaid()) {
            throw new TicketException("Ticket has already been paid, try to cancel the whole order");
        }
        if (user != null) {
            cartService.removeTicket(user.getId(), ticket);
        }
        showService.removeTicket(ticket.getShow().getId(), ticket);
        ticketRepository.deleteById(id);
    }

}
