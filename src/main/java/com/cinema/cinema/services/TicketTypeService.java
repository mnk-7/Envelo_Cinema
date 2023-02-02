package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.TicketTypeException;
import com.cinema.cinema.models.TicketType;
import com.cinema.cinema.repositories.TicketTypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TicketTypeService {

    private TicketTypeRepository ticketTypeRepository;

    public TicketType getTicketType(long id) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(id);
        if (ticketType.isEmpty()) {
            throw new TicketTypeException("Ticket type with given ID not found");
        }
        return ticketType.get();
    }

    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }

    public List<TicketType> getAllActiveTicketTypes() {
        return ticketTypeRepository.findAllByIsAvailableTrue();
    }

    public void addTicketType(String name, String description, BigDecimal price, boolean isAvailable) {
        TicketType ticketType = new TicketType();
        ticketType.setName(name);
        ticketType.setDescription(description);
        ticketType.setPrice(price);
        ticketType.setAvailable(isAvailable);
        ticketTypeRepository.save(ticketType);
    }

    public void editTicketType(long id, String name, String description, BigDecimal price, boolean isAvailable) {
        TicketType ticketType = getTicketType(id);
        ticketType.setName(name);
        ticketType.setDescription(description);
        ticketType.setPrice(price);
        ticketType.setAvailable(isAvailable);
        ticketTypeRepository.save(ticketType);
    }

}
