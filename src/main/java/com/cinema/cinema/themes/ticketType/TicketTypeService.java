package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoRead;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;
    private final DtoMapperService mapperService;
    private final Validator validator;


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
    public TicketTypeDtoRead getTicketType(long id) {
        TicketType ticketType = validateTicketTypeExists(id);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public TicketTypeDtoRead addTicketType(TicketTypeDtoWrite ticketTypeDto) {
        validateTicketTypeNotExists(ticketTypeDto);
        TicketType ticketType = mapperService.mapToTicketType(ticketTypeDto);
        validateInputForTicketType(ticketType);
        ticketType = ticketTypeRepository.save(ticketType);
        return mapperService.mapToTicketTypeDto(ticketType);
    }

    @Transactional
    public void editTicketType(long id, TicketTypeDtoWrite ticketTypeDto) {
        TicketType ticketType = validateTicketTypeExists(id);
        TicketType ticketTypeFromDto = mapperService.mapToTicketType(ticketTypeDto);
        validateInputForTicketType(ticketTypeFromDto);
        validateTicketTypeChanged(ticketType, ticketTypeFromDto);
        validateTicketTypeNotExists(id, ticketTypeDto);
        ticketTypeFromDto.setId(ticketType.getId());
        ticketTypeRepository.save(ticketTypeFromDto);
    }

    private void validateInputForTicketType(TicketType ticketType) {
        Set<ConstraintViolation<TicketType>> violations = validator.validate(ticketType);
        if (!violations.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation<TicketType> violation : violations) {
                messages.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ArgumentNotValidException("Not valid data provided", messages);
        }
    }

    private TicketType validateTicketTypeExists(long id) {
        Optional<TicketType> ticketType = ticketTypeRepository.findById(id);
        if (ticketType.isEmpty()) {
            throw new ElementNotFoundException("Ticket type with ID " + id + " not found");
        }
        return ticketType.get();
    }

    private void validateTicketTypeNotExists(TicketTypeDtoWrite ticketTypeDto) {
        Optional<TicketType> ticketType = ticketTypeRepository.findByName(ticketTypeDto.getName());
        if (ticketType.isPresent()) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

    private void validateTicketTypeNotExists(long id, TicketTypeDtoWrite ticketTypeDto) {
        Optional<TicketType> ticketType = ticketTypeRepository.findByName(ticketTypeDto.getName());
        if (ticketType.isPresent() && ticketType.get().getId() != id) {
            throw new ElementFoundException("Ticket type with name " + ticketType.get().getName() + " already exists");
        }
    }

    private void validateTicketTypeChanged(TicketType ticketType, TicketType ticketTypeFromDto) {
        if (ticketType.equals(ticketTypeFromDto)) {
            throw new ElementNotModifiedException("No change detected, ticket type with name " + ticketType.getName() + " has not been modified");
        }
    }

}
