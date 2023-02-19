package com.cinema.cinema.themes.ticket;

import com.cinema.cinema.themes.ticket.model.TicketInputDto;
import com.cinema.cinema.themes.ticket.model.TicketOutputDto;
import com.cinema.cinema.themes.ticket.model.TicketShortDto;
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

@AllArgsConstructor
@RestController
@Tag(name = "Tickets")
@RequestMapping("/${app.prefix}/${app.version}/tickets")
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{ticketId}")
    @Operation(summary = "Get ticket by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket returned"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")})
    public ResponseEntity<TicketOutputDto> getTicket(@PathVariable long ticketId) {
        TicketOutputDto ticket = ticketService.getTicket(ticketId);
        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addTicket(@RequestParam(required = false) Long userId, @RequestBody TicketInputDto ticket) {
        TicketShortDto ticketCreated = ticketService.addTicket(userId, ticket);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ticketId}")
                .buildAndExpand(ticketCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{ticketId}")
    @Operation(summary = "Update ticket type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")})
    public ResponseEntity<Void> editTicketType(@RequestParam long ticketId, @RequestBody TicketInputDto ticket) {
        ticketService.editTicketType(ticketId, ticket);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{ticketId}")
    @Operation(summary = "Delete ticket")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ticket deleted"),
            @ApiResponse(responseCode = "404", description = "Ticket not found")})
    public ResponseEntity<Void> removeTicket(@RequestParam(required = false) Long userId, @PathVariable long ticketId) {
        ticketService.removeTicket(userId, ticketId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
