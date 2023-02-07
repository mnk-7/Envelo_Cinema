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

    private final GenreService service;

    @GetMapping
    @Operation(summary = "Get all genres")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with genres returned"),
            @ApiResponse(responseCode = "204", description = "No genre found")})
    public ResponseEntity<List<GenreDtoRead>> getAllGenres() {
        List<GenreDtoRead> genres = service.getAllGenres();
        HttpStatus status = genres.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(genres, status);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get genre by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre returned"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<GenreDtoRead> getGenre(@PathVariable long id) {
        GenreDtoRead genre = service.getGenreById(id);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Genre created"),
            @ApiResponse(responseCode = "400", description = "Genre already exists")})
    public ResponseEntity<Void> addGenre(@RequestBody GenreDtoWrite genre) {
        GenreDtoRead genreCreated = service.addGenre(genre);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(genreCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update genre")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Genre updated"),
            @ApiResponse(responseCode = "400", description = "Genre already exists"),
            @ApiResponse(responseCode = "404", description = "Genre not found")})
    public ResponseEntity<Void> editGenre(@PathVariable long id, @RequestBody GenreDtoWrite genre) {
        service.editGenre(id, genre);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
