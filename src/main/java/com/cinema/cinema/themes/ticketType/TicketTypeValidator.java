package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketTypeValidator extends ValidatorService<TicketType> {

    private final TicketTypeRepository repository;

    public TicketTypeValidator(Validator validator, TicketTypeRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public TicketType validateExists(long id) {
        Optional<TicketType> ticketType = repository.findById(id);
        if (ticketType.isEmpty()) {
            throw new ElementNotFoundException("Ticket type with ID " + id + " not found");
        }
        return ticketType.get();
    }

    public void validateNotExists(TicketType ticketTypeFromDto) {
        Optional<TicketType> ticketType = repository.findByName(ticketTypeFromDto.getName());
        if (ticketType.isPresent()) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

    public void validateChanged(TicketType ticketType, TicketType ticketTypeFromDto) {
        if (ticketType.equals(ticketTypeFromDto)) {
            throw new ElementNotModifiedException("No change detected, ticket type with name " + ticketType.getName() + " has not been modified");
        }
    }

    public void validateNotExists(long id, String name) {
        Optional<TicketType> ticketType = repository.findByName(name);
        if (ticketType.isPresent() && ticketType.get().getId() != id) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

}
