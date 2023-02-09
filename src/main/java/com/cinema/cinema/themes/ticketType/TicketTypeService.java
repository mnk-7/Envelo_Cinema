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

    private final TicketTypeRepository ticketTypeRepository;
    private final TicketTypeValidator ticketTypeValidator;
    private final DtoMapperService mapperService;


    @Transactional(readOnly = true)
    public List<TicketTypeDtoRead> getAllTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAll(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TicketTypeDtoRead> getAllActiveTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeRepository.findAllByIsAvailableTrue(Sort.by("name"));
        return ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public TicketTypeDtoRead getTicketType(long ticketTypeId) {
        TicketType ticketType = ticketTypeValidator.validateExists(ticketTypeId);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public TicketTypeDtoRead addTicketType(TicketTypeDtoWrite ticketTypeDto) {
        TicketType ticketType = mapperService.mapToTicketType(ticketTypeDto);
        ticketTypeValidator.validateNotExists(ticketType);
        ticketTypeValidator.validateInput(ticketType);
        ticketType = ticketTypeRepository.save(ticketType);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public void editTicketType(long ticketTypeId, TicketTypeDtoWrite ticketTypeDto) {
        TicketType ticketType = ticketTypeValidator.validateExists(ticketTypeId);
        TicketType ticketTypeFromDto = mapperService.mapToTicketType(ticketTypeDto);
        ticketTypeValidator.validateInput(ticketTypeFromDto);
        ticketTypeValidator.validateChanged(ticketType, ticketTypeFromDto);
        ticketTypeValidator.validateNotExists(ticketTypeId, ticketTypeDto.getName());
        setFields(ticketType, ticketTypeDto);
        ticketTypeRepository.save(ticketType);
    }

    private void setFields(TicketType ticketType, TicketTypeDtoWrite ticketTypeDto) {
        ticketType.setName(ticketTypeDto.getName());
        ticketType.setDescription(ticketTypeDto.getDescription());
        ticketType.setPrice(ticketTypeDto.getPrice());
        ticketType.setAvailable(ticketTypeDto.isAvailable());
    }

}
