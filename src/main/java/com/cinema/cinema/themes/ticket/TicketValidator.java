package com.cinema.cinema.themes.ticket;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.seat.model.SingleSeat;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.seat.model.Seat;
import com.cinema.cinema.themes.show.ShowService;
import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketValidator extends ValidatorService<Ticket> {

    private final TicketRepository ticketRepository;
    private final ShowService showService;

    public TicketValidator(Validator validator, TicketRepository ticketRepository, ShowService showService) {
        super(validator);
        this.ticketRepository = ticketRepository;
        this.showService = showService;
    }

    @Override
    public Ticket validateExists(long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);
        if (ticket.isEmpty()) {
            throw new ElementNotFoundException("Ticket with ID " + ticketId + " not found");
        }
        return ticket.get();
    }

    public void validateNotPaid(Ticket ticket) {
        if (ticket.isPaid()) {
            throw new ProcessingException("Ticket has already been paid, you cannot modify or remove it");
        }
    }

    public TicketType validateTicketTypeNotEmpty(TicketType ticketType) {
        if (ticketType.getId() == null) {
            throw new ProcessingException("Ticket type cannot be empty");
        }
        return ticketType;
    }

    public void validateSeat(Seat seat, Show show) {
        validateSeatInVenue(seat, show);
        validateSeatNotPartOfDouble(seat);
        validateSeatAvailable(seat, show);
    }

    private void validateSeatInVenue(Seat seat, Show show) {
        if (!seat.getVenue().getId().equals(show.getVenue().getId())) {
            throw new ProcessingException("Seat didn't find in the show venue");
        }
    }

    private void validateSeatAvailable(Seat seat, Show show) {
        List<Ticket> tickets = showService.getAllTickets(show.getId());
        Optional<Seat> seatAvailable = tickets
                .stream()
                .map(Ticket::getSeat)
                .filter(s -> s.getId().equals(seat.getId()))
                .findFirst();
        if (seatAvailable.isPresent()) {
            throw new ProcessingException("Seat is not available");
        }
    }

    private void validateSeatNotPartOfDouble(Seat seat) {
        if (seat instanceof SingleSeat) {
            if (((SingleSeat) seat).isPartOfCombinedSeat()) {
                throw new ProcessingException("Seat is part of combined seat");
            }
        }
    }

    public void validateNotInOrder(Ticket ticket) {
        if (ticket.getOrder() != null) {
            throw new ProcessingException("Ticket is part of another order");
        }
    }

}
