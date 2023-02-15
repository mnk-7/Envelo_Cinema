package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketTypeValidator extends ValidatorService<TicketType> {

    private final TicketTypeRepository ticketTypeRepository;

    public TicketTypeValidator(Validator validator, TicketTypeRepository ticketTypeRepository) {
        super(validator);
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public TicketType validateExists(long ticketTypeId) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(ticketTypeId);
        if (ticketType.isEmpty()) {
            throw new ElementNotFoundException("Ticket type with ID " + ticketTypeId + " not found");
        }
        return ticketType.get();
    }

    public void validateNotExists(TicketType ticketTypeFromDto) {
        Optional<TicketType> ticketType = ticketTypeRepository.findByName(ticketTypeFromDto.getName());
        if (ticketType.isPresent()) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

    public void validateChanged(TicketType ticketType, TicketType ticketTypeFromDto) {
        if (ticketType.equals(ticketTypeFromDto)) {
            throw new ElementNotModifiedException("No change detected, ticket type with name " + ticketType.getName() + " has not been modified");
        }
    }

    public void validateNotExists(long ticketTypeId, String name) {
        Optional<TicketType> ticketType = ticketTypeRepository.findByName(name);
        if (ticketType.isPresent() && ticketType.get().getId() != ticketTypeId) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

    public void validateIsAvailable(TicketType ticketType) {
        if (!ticketType.isAvailable()) {
            throw new ProcessingException("Ticket type " + ticketType.getName() + " is not available anymore");
        }
    }

}
