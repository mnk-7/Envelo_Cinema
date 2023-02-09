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

    private final TicketTypeService ticketTypeService;

    @GetMapping
    @Operation(summary = "Get all ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No ticket type found")})
    public ResponseEntity<List<TicketTypeDtoRead>> getAllTicketTypes() {
        List<TicketTypeDtoRead> ticketTypes = ticketTypeService.getAllTicketTypes();
        HttpStatus status = ticketTypes.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypes, status);
    }

    @GetMapping("/active")
    @Operation(summary = "Get all active ticket types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with active ticket types returned"),
            @ApiResponse(responseCode = "204", description = "No active ticket type found")})
    public ResponseEntity<List<TicketTypeDtoRead>> getAllActiveTicketTypes() {
        List<TicketTypeDtoRead> ticketTypes = ticketTypeService.getAllActiveTicketTypes();
        HttpStatus status = ticketTypes.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketTypes, status);
    }

    @GetMapping("/{ticketTypeId}")
    @Operation(summary = "Get ticket type by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type returned"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<TicketTypeDtoRead> getTicketType(@PathVariable long ticketTypeId) {
        TicketTypeDtoRead ticketType = ticketTypeService.getTicketType(ticketTypeId);
        return new ResponseEntity<>(ticketType, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket type created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addTicketType(@RequestBody TicketTypeDtoWrite ticketType) {
        TicketTypeDtoRead ticketTypeCreated = ticketTypeService.addTicketType(ticketType);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ticketTypeId}")
                .buildAndExpand(ticketTypeCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{ticketTypeId}")
    @Operation(summary = "Update ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket type updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Ticket type not found")})
    public ResponseEntity<Void> editTicketType(@PathVariable long ticketTypeId, @RequestBody TicketTypeDtoWrite ticketType) {
        ticketTypeService.editTicketType(ticketTypeId, ticketType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
