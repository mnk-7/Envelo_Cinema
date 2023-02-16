package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.show.model.ShowInputDto;
import com.cinema.cinema.themes.show.model.ShowOutputDto;
import com.cinema.cinema.themes.show.model.ShowShortDto;
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
import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Shows")
@RequestMapping("/${app.prefix}/${app.version}/shows")
public class ShowController {

    private final ShowService showService;

    @GetMapping
    @Operation(summary = "Get all shows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with shows returned"),
            @ApiResponse(responseCode = "204", description = "No show found")})
    public ResponseEntity<List<ShowShortDto>> getAllShows() {
        List<ShowShortDto> shows = showService.getAllShows();
        HttpStatus status = shows.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(shows, status);
    }

    @GetMapping("/current")
    @Operation(summary = "Get all shows for current week")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with shows returned"),
            @ApiResponse(responseCode = "204", description = "No show found")})
    public ResponseEntity<List<ShowShortDto>> getAllShowsForCurrentWeek() {
        List<ShowShortDto> shows = showService.getAllShowsForCurrentWeek();
        HttpStatus status = shows.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(shows, status);
    }

    @GetMapping("/{showId}")
    @Operation(summary = "Get show by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show returned"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<ShowShortDto> getShow(@PathVariable long showId) {
        ShowShortDto show = showService.getShow(showId);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @GetMapping("/{showId}/details")
    @Operation(summary = "Get show by its ID with reservation details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show returned"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<ShowOutputDto> getShowWithVenueDetails(@PathVariable long showId) {
        ShowOutputDto show = showService.getShowWithVenueDetails(showId);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @GetMapping("/{showId}/tickets")
    @Operation(summary = "Get all tickets for show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with tickets returned"),
            @ApiResponse(responseCode = "204", description = "No ticket found")})
    public ResponseEntity<List<TicketShortDto>> getAllTickets(@PathVariable long showId) {
        List<TicketShortDto> tickets = showService.getAllTickets(showId);
        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addGenre(@RequestBody ShowInputDto show) {
        ShowShortDto showCreated = showService.addShow(show);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{showId}")
                .buildAndExpand(showCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{showId}")
    @Operation(summary = "Update show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<Void> editShow(@PathVariable long showId, @RequestBody ShowInputDto show) {
        showService.editShow(showId, show);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{showId}")
    @Operation(summary = "Delete show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Show deleted"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<Void> cancelShow(@PathVariable long showId) {
        showService.cancelShow(showId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
