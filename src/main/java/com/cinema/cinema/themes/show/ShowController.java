package com.cinema.cinema.themes.show;

import com.cinema.cinema.themes.show.model.Show;
import com.cinema.cinema.themes.show.model.ShowInputDto;
import com.cinema.cinema.themes.show.model.ShowOutputDto;
import com.cinema.cinema.themes.show.model.ShowShortDto;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.ticket.model.TicketShortDto;
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
@Tag(name = "Shows")
@RequestMapping("/${app.prefix}/${app.version}/shows")
public class ShowController {

    private final ShowService showService;
    private DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all shows")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with shows returned"),
            @ApiResponse(responseCode = "204", description = "No show found")})
    public ResponseEntity<List<ShowShortDto>> getAllShows() {
        List<Show> shows = showService.getAllShows();
        List<ShowShortDto> showsDto = shows.stream()
                .map(mapperService::mapToShowShortDto)
                .toList();
        HttpStatus status = showsDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(showsDto, status);
    }

    @GetMapping("/current")
    @Operation(summary = "Get all shows for current week")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with shows returned"),
            @ApiResponse(responseCode = "204", description = "No show found")})
    public ResponseEntity<List<ShowShortDto>> getAllShowsForCurrentWeek() {
        List<Show> shows = showService.getAllShowsForCurrentWeek();
        List<ShowShortDto> showsDto = shows.stream()
                .map(mapperService::mapToShowShortDto)
                .toList();
        HttpStatus status = showsDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(showsDto, status);
    }

    @GetMapping("/{showId}")
    @Operation(summary = "Get show by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show returned"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<ShowShortDto> getShow(@PathVariable long showId) {
        Show show = showService.getShow(showId);
        ShowShortDto showDto = mapperService.mapToShowShortDto(show);
        return new ResponseEntity<>(showDto, HttpStatus.OK);
    }

    @GetMapping("/{showId}/details")
    @Operation(summary = "Get show by its ID with reservation details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show returned"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<ShowOutputDto> getShowWithVenueDetails(@PathVariable long showId) {
        Show show = showService.getShowWithVenueDetails(showId);
        ShowOutputDto showDto = mapperService.mapToShowDto(show);
        return new ResponseEntity<>(showDto, HttpStatus.OK);
    }

    @GetMapping("/{showId}/tickets")
    @Operation(summary = "Get all tickets for show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with tickets returned"),
            @ApiResponse(responseCode = "204", description = "No ticket found")})
    public ResponseEntity<List<TicketShortDto>> getAllTickets(@PathVariable long showId) {
        List<Ticket> tickets = showService.getAllTickets(showId);
        List<TicketShortDto> ticketsDto = tickets.stream()
                .map(mapperService::mapToTicketShortDto)
                .toList();
        HttpStatus status = ticketsDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ticketsDto, status);
    }

    @PostMapping
    @Operation(summary = "Create show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Show created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addShow(@RequestBody ShowInputDto showDto) {
        Show show = mapperService.mapToShow(showDto);
        show = showService.addShow(show);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{showId}")
                .buildAndExpand(show.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{showId}")
    @Operation(summary = "Update show")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Show updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Show not found")})
    public ResponseEntity<Void> editShow(@PathVariable long showId, @RequestBody ShowInputDto showDto) {
        Show show = mapperService.mapToShow(showDto);
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
