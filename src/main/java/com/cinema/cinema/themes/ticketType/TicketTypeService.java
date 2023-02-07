package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoRead;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class TicketTypeService {

    private final TicketTypeRepository repository;
    private final TicketTypeValidator validator;
    private final DtoMapperService mapper;


    @Transactional(readOnly = true)
    public List<TicketTypeDtoRead> getAllTicketTypes() {
        List<TicketType> ticketTypes = repository.findAll(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapper::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketTypeDtoRead> getAllActiveTicketTypes() {
        List<TicketType> ticketTypes = repository.findAllByIsAvailableTrue(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapper::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketTypeDtoRead getTicketType(long id) {
        TicketType ticketType = validator.validateExists(id);
        return mapper.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public TicketTypeDtoRead addTicketType(TicketTypeDtoWrite ticketTypeDto) {
        TicketType ticketType = mapper.mapToTicketType(ticketTypeDto);
        validator.validateNotExists(ticketType);
        validator.validateInput(ticketType);
        ticketType = repository.save(ticketType);
        return mapper.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public void editTicketType(long id, TicketTypeDtoWrite ticketTypeDto) {
        TicketType ticketType = validator.validateExists(id);
        TicketType ticketTypeFromDto = mapper.mapToTicketType(ticketTypeDto);
        validator.validateInput(ticketTypeFromDto);
        validator.validateChanged(ticketType, ticketTypeFromDto);
        validator.validateNotExists(id, ticketTypeDto.getName());
        setFields(ticketType, ticketTypeDto);
        repository.save(ticketType);
    }

    private void setFields(TicketType ticketType, TicketTypeDtoWrite ticketTypeDto) {
        ticketType.setName(ticketTypeDto.getName());
        ticketType.setDescription(ticketTypeDto.getDescription());
        ticketType.setPrice(ticketTypeDto.getPrice());
        ticketType.setAvailable(ticketTypeDto.isAvailable());
    }

}
