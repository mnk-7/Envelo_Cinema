package com.cinema.cinema.themes.ageRestriction;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionInputDto;
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
@Tag(name = "Age restrictions")
@RequestMapping("/${app.prefix}/${app.version}/age-restrictions")
public class AgeRestrictionController {

    private final AgeRestrictionService ageRestrictionService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all age restrictions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with age restrictions returned"),
            @ApiResponse(responseCode = "204", description = "No age restriction found")})
    public ResponseEntity<List<AgeRestrictionOutputDto>> getAllAgeRestrictions() {
        List<AgeRestriction> ageRestrictions = ageRestrictionService.getAllAgeRestrictions();
        List<AgeRestrictionOutputDto> ageRestrictionDto = ageRestrictions.stream()
                .map(mapperService::mapToAgeRestrictionDto)
                .toList();
        HttpStatus status = ageRestrictions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ageRestrictionDto, status);
    }

    @GetMapping("/{ageRestrictionId}")
    @Operation(summary = "Get age restriction by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Age restriction returned"),
            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
    public ResponseEntity<AgeRestrictionOutputDto> getAgeRestriction(@PathVariable long ageRestrictionId) {
        AgeRestriction ageRestriction = ageRestrictionService.getAgeRestriction(ageRestrictionId);
        AgeRestrictionOutputDto ageRestrictionDto = mapperService.mapToAgeRestrictionDto(ageRestriction);
        return new ResponseEntity<>(ageRestrictionDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Age restriction created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addAgeRestriction(@RequestBody AgeRestrictionInputDto ageRestrictionDto) {
        AgeRestriction ageRestriction = mapperService.mapToAgeRestriction(ageRestrictionDto);
        ageRestriction = ageRestrictionService.addAgeRestriction(ageRestriction);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{ageRestrictionId}")
                .buildAndExpand(ageRestriction.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{ageRestrictionId}")
    @Operation(summary = "Update age restriction")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Age restriction updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
    public ResponseEntity<Void> editAgeRestriction(@PathVariable long ageRestrictionId, @RequestBody AgeRestrictionInputDto ageRestrictionDto) {
        AgeRestriction ageRestriction = mapperService.mapToAgeRestriction(ageRestrictionDto);
        ageRestrictionService.editAgeRestriction(ageRestrictionId, ageRestriction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @DeleteMapping("/{ageRestrictionId}")
//    @Operation(summary = "Delete age restriction")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Age restriction deleted"),
//            @ApiResponse(responseCode = "404", description = "Age restriction not found")})
//    public ResponseEntity<Void> removeAgeRestriction(@PathVariable long ageRestrictionId) {
//        ageRestrictionService.removeAgeRestriction(ageRestrictionId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }

}
