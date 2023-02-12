package com.cinema.cinema.themes.venue;

import com.cinema.cinema.themes.venue.model.Venue;
import com.cinema.cinema.themes.venue.model.VenueInputDto;
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
@Tag(name = "Venues")
@RequestMapping("/${app.prefix}/${app.version}/venues")
public class VenueController {

    private final VenueService venueService;

    @GetMapping
    @Operation(summary = "Get all venues")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with venues returned"),
            @ApiResponse(responseCode = "204", description = "No venue found")})
    public ResponseEntity<List<Venue>> getAllVenues() {
        List<Venue> genres = venueService.getAllVenues();
        HttpStatus status = genres.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(genres, status);
    }

    @GetMapping("/{venueId}")
    @Operation(summary = "Get venue by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue returned"),
            @ApiResponse(responseCode = "404", description = "Venue not found")})
    public ResponseEntity<Venue> getVenue(@PathVariable long venueId) {
        Venue venue = venueService.getVenue(venueId);
        return new ResponseEntity<>(venue, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create venue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venue created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addVenue(@RequestBody VenueInputDto venue) {
        Venue venueCreated = venueService.addVenue(venue);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{venueId}")
                .buildAndExpand(venueCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{venueId}")
    @Operation(summary = "Update venue structure")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Venue not found")})
    public ResponseEntity<Void> editVenueStructure(@PathVariable long venueId, @RequestBody VenueInputDto venue) {
        venueService.editVenueStructure(venueId, venue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{venueId}/name")
    @Operation(summary = "Update venue name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Venue not found")})
    public ResponseEntity<Void> editVenueName(@PathVariable long venueId, @RequestBody VenueInputDto venue) {
        venueService.editVenueName(venueId, venue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{venueId}")
    @Operation(summary = "Deactivate venue")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venue deactivated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Venue not found")})
    public ResponseEntity<Void> removeVenue(@PathVariable long venueId) {
        venueService.removeVenue(venueId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
