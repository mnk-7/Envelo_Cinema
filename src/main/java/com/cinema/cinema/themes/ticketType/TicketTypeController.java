package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.themes.ticketType.model.TicketType;
import com.cinema.cinema.themes.ticketType.model.TicketTypeOutputDto;
import com.cinema.cinema.themes.ticketType.model.TicketTypeInputDto;
import com.cinema.cinema.utils.DtoMapperService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Ticket types")
@RequestMapping("/${app.prefix}/${app.version}/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No ticket type found")})
    public ResponseEntity<List<TicketTypeOutputDto>> getAllTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeService.getAllTicketTypes();
        List<TicketTypeOutputDto> ticketTypesDto = ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
        HttpStatus status = ticketTypesDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypesDto, status);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with active ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No active ticket type found")})
    public ResponseEntity<List<TicketTypeOutputDto>> getAllActiveTicketTypes() {
        List<TicketType> ticketTypes = ticketTypeService.getAllActiveTicketTypes();
        List<TicketTypeOutputDto> ticketTypesDto = ticketTypes.stream()
                .map(mapperService::mapToTicketTypeDto)
                .toList();
        HttpStatus status = ticketTypesDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypesDto, status);
    }

    @GetMapping("/{ticketTypeId}")
    @Operation(summary = "Get ticket type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type returned"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<TicketTypeOutputDto> getTicketType(@PathVariable long ticketTypeId) {
        TicketType ticketType = ticketTypeService.getTicketType(ticketTypeId);
        TicketTypeOutputDto ticketTypeDto = mapperService.mapToTicketTypeDto(ticketType);
        return new ResponseEntity<>(ticketTypeDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket type created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addTicketType(@RequestBody TicketTypeInputDto ticketTypeDto) {
        TicketType ticketType = mapperService.mapToTicketType(ticketTypeDto);
        ticketType = ticketTypeService.addTicketType(ticketType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ticketTypeId}")
                .buildAndExpand(ticketType.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{ticketTypeId}")
    @Operation(summary = "Update ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<Void> editTicketType(@PathVariable long ticketTypeId, @RequestBody TicketTypeInputDto ticketTypeDto) {
        TicketType ticketType = mapperService.mapToTicketType(ticketTypeDto);
        ticketTypeService.editTicketType(ticketTypeId, ticketType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
