package com.cinema.cinema.themes.genre;

import com.cinema.cinema.themes.genre.model.GenreDtoRead;
import com.cinema.cinema.themes.genre.model.GenreDtoWrite;
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

    @GetMapping
    @Operation(summary = "Get all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with genres returned"),
            @ApiResponse(responseCode = "204", description = "No genre found")})
    public ResponseEntity<List<GenreDtoRead>> getAllGenres() {
        List<GenreDtoRead> genres = genreService.getAllGenres();
        HttpStatus status = genres.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(genres, status);
    }

    @GetMapping("/{genreId}")
    @Operation(summary = "Get genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre returned"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<GenreDtoRead> getGenre(@PathVariable long genreId) {
        GenreDtoRead genre = genreService.getGenreById(genreId);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addGenre(@RequestBody GenreDtoWrite genre) {
        GenreDtoRead genreCreated = genreService.addGenre(genre);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{genreId}")
                .buildAndExpand(genreCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{genreId}")
    @Operation(summary = "Update genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<Void> editGenre(@PathVariable long genreId, @RequestBody GenreDtoWrite genre) {
        genreService.editGenre(genreId, genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
