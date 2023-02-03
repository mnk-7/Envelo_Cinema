package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoRead;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionDtoWrite;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Tag(name = "Age restrictions")
@RequestMapping("/${app.prefix}/${app.version}/age-restrictions")
public class AgeRestrictionController {

    private final AgeRestrictionService ageRestrictionService;

    @GetMapping
    @Operation(summary = "Get all age restrictions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with age restrictions returned"),
            @ApiResponse(responseCode = "204", description = "No age restriction found")})
    public ResponseEntity<List<AgeRestrictionDtoRead>> getAllAgeRestrictions() {
        List<AgeRestrictionDtoRead> allAgeRestrictions = ageRestrictionService.getAllAgeRestrictions();
        HttpStatus status = allAgeRestrictions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(allAgeRestrictions, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get age restriction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Age restriction returned"),
            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
    public ResponseEntity<AgeRestrictionDtoRead> getAgeRestriction(@PathVariable long id) {
        AgeRestrictionDtoRead ageRestrictionDto = ageRestrictionService.getAgeRestrictionById(id);
        return new ResponseEntity<>(ageRestrictionDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Age restriction created"),
            @ApiResponse(responseCode = "400", description = "Age restriction already exists")})
    public ResponseEntity<Void> addAgeRestriction(@RequestBody AgeRestrictionDtoWrite ageRestrictionDtoW) {
        AgeRestrictionDtoRead ageRestrictionDtoR = ageRestrictionService.addAgeRestriction(ageRestrictionDtoW);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(ageRestrictionDtoR.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Age restriction updated"),
            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
    public ResponseEntity<Void> editAgeRestriction(@PathVariable long id, @RequestBody AgeRestrictionDtoWrite ageRestrictionDto) {
        ageRestrictionService.editAgeRestriction(id, ageRestrictionDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Age restriction deleted"),
            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
    public ResponseEntity<Void> removeAgeRestriction(@PathVariable long id) {
        ageRestrictionService.removeAgeRestriction(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
