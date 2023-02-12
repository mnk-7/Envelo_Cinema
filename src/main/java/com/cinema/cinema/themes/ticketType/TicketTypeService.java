package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeOutputDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeInputDto;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final TicketTypeValidator ticketTypeValidator;
    private final DtoMapperService mapperService;


    @Transactional(readOnly = true)
    public List<TicketTypeOutputDto> getAllTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAll(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketTypeOutputDto> getAllActiveTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAllByIsAvailableTrue(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketTypeOutputDto getTicketType(long ticketTypeId) {
        TicketType ticketType = ticketTypeValidator.validateExists(ticketTypeId);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public TicketTypeOutputDto addTicketType(TicketTypeInputDto ticketTypeDto) {
        TicketType ticketType = mapperService.mapToTicketType(ticketTypeDto);
        ticketTypeValidator.validateNotExists(ticketType);
        ticketTypeValidator.validateInput(ticketType);
        ticketType = ticketTypeRepository.save(ticketType);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public void editTicketType(long ticketTypeId, TicketTypeInputDto ticketTypeDto) {
        TicketType ticketType = ticketTypeValidator.validateExists(ticketTypeId);
        TicketType ticketTypeFromDto = mapperService.mapToTicketType(ticketTypeDto);
        ticketTypeValidator.validateInput(ticketTypeFromDto);
        ticketTypeValidator.validateChanged(ticketType, ticketTypeFromDto);
        ticketTypeValidator.validateNotExists(ticketTypeId, ticketTypeDto.getName());
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
