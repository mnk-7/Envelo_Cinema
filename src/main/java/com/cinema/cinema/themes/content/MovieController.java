package com.cinema.cinema.themes.content;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieDtoRead;
import com.cinema.cinema.themes.content.model.MovieDtoWrite;
import com.cinema.cinema.themes.user.service.StandardUserService;
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
@Tag(name = "Movies")
@RequestMapping("/${app.prefix}/${app.version}/movies")
public class MovieController {

    private final MovieService movieService;
    private final StandardUserService userService;

    @GetMapping
    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with movies returned"),
            @ApiResponse(responseCode = "204", description = "No movie found")})
    public ResponseEntity<List<MovieDtoRead>> getAllMovies() {
        List<MovieDtoRead> movies = movieService.getAllContents();
        HttpStatus status = movies.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(movies, status);
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Get movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie returned"),
            @ApiResponse(responseCode = "404", description = "Movie not found")})
    public ResponseEntity<MovieDtoRead> getMovie(@PathVariable long movieId) {
        MovieDtoRead movie = movieService.getContent(movieId);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addMovie(@RequestBody MovieDtoWrite movie) {
        MovieDtoRead movieCreated = movieService.addContent(movie);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{movieId}")
                .buildAndExpand(movieCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{movieId}")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie not found")})
    public ResponseEntity<Void> editMovie(long movieId, @RequestBody MovieDtoWrite movie) {
        movieService.editContent(movieId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO - perform tests
    @PutMapping("/{movieId}/rating")
    @Operation(summary = "Rate movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie and user updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> rateMovie(long movieId, @RequestParam int rate, @RequestParam int userId) {
        Movie movie = movieService.getMovie(movieId);
        userService.rateMovie(userId, movie, rate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
