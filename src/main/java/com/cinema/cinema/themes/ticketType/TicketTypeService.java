package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.themes.ticketType.model.TicketType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final TicketTypeValidator ticketTypeValidator;

    @Transactional(readOnly = true)
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll(Sort.by("name"));
    }

    @Transactional(readOnly = true)
    public List<TicketType> getAllActiveTicketTypes() {
        return ticketTypeRepository.findAllByIsAvailableTrue(Sort.by("name"));
    }

    @Transactional(readOnly = true)
    public TicketType getTicketType(long ticketTypeId) {
        return ticketTypeValidator.validateExists(ticketTypeId);
    }

    @Transactional
    public TicketType addTicketType(TicketType ticketType) {
        ticketTypeValidator.validateNotExists(ticketType);
        ticketTypeValidator.validateInput(ticketType);
        return ticketTypeRepository.save(ticketType);
    }

    @Transactional
    public void editTicketType(long ticketTypeId, TicketType ticketTypeFromDto) {
        TicketType ticketType = ticketTypeValidator.validateExists(ticketTypeId);
        ticketTypeValidator.validateInput(ticketTypeFromDto);
        ticketTypeValidator.validateChanged(ticketType, ticketTypeFromDto);
        ticketTypeValidator.validateNotExists(ticketTypeId, ticketTypeFromDto.getName());
        setFields(ticketType, ticketTypeFromDto);
        ticketTypeRepository.save(ticketType);
    }

    private void setFields(TicketType ticketType, TicketType ticketTypeFromDto) {
        ticketType.setName(ticketTypeFromDto.getName());
        ticketType.setDescription(ticketTypeFromDto.getDescription());
        ticketType.setPrice(ticketTypeFromDto.getPrice());
        ticketType.setAvailable(ticketTypeFromDto.isAvailable());
    }

}
