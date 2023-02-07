package com.cinema.cinema.themes.ticketType;

import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoRead;
import com.cinema.cinema.themes.ticketType.model.TicketTypeDtoWrite;
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

    private final TicketTypeService service;

    @GetMapping
    @Operation(summary = "Get all ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No ticket type found")})
    public ResponseEntity<List<TicketTypeDtoRead>> getAllTicketTypes() {
        List<TicketTypeDtoRead> ticketTypes = service.getAllTicketTypes();
        HttpStatus status = ticketTypes.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypes, status);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with active ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No active ticket type found")})
    public ResponseEntity<List<TicketTypeDtoRead>> getAllActiveTicketTypes() {
        List<TicketTypeDtoRead> ticketTypes = service.getAllActiveTicketTypes();
        HttpStatus status = ticketTypes.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypes, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get ticket type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type returned"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<TicketTypeDtoRead> getTicketType(@PathVariable long id) {
        TicketTypeDtoRead ticketType = service.getTicketType(id);
        return new ResponseEntity<>(ticketType, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket type created"),
            @ApiResponse(responseCode = "400", description = "Ticket type already exists")})
    public ResponseEntity<Void> addTicketType(@RequestBody TicketTypeDtoWrite ticketType) {
        TicketTypeDtoRead ticketTypeCreated = service.addTicketType(ticketType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ticketTypeCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type updated"),
            @ApiResponse(responseCode = "400", description = "Ticket type already exists"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<Void> editTicketType(@PathVariable long id, @RequestBody TicketTypeDtoWrite ticketType) {
        service.editTicketType(id, ticketType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
