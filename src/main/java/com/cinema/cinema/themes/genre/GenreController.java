package com.cinema.cinema.themes.genre;

import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreOutputDto;
import com.cinema.cinema.themes.genre.model.GenreInputDto;
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
@Tag(name = "Genres")
@RequestMapping("/${app.prefix}/${app.version}/genres")
public class GenreController {

    private final GenreService genreService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with genres returned"),
            @ApiResponse(responseCode = "204", description = "No genre found")})
    public ResponseEntity<List<GenreOutputDto>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        List<GenreOutputDto> genresDto = genres.stream()
                .map(mapperService::mapToGenreDto)
                .toList();
        HttpStatus status = genresDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(genresDto, status);
    }

    @GetMapping("/{genreId}")
    @Operation(summary = "Get genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre returned"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<GenreOutputDto> getGenre(@PathVariable long genreId) {
        Genre genre = genreService.getGenre(genreId);
        GenreOutputDto genreDto = mapperService.mapToGenreDto(genre);
        return new ResponseEntity<>(genreDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addGenre(@RequestBody GenreInputDto genreDto) {
        Genre genre = mapperService.mapToGenre(genreDto);
        genre = genreService.addGenre(genre);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{genreId}")
                .buildAndExpand(genre.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{genreId}")
    @Operation(summary = "Update genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<Void> editGenre(@PathVariable long genreId, @RequestBody GenreInputDto genreDto) {
        Genre genre = mapperService.mapToGenre(genreDto);
        genreService.editGenre(genreId, genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
