package com.cinema.cinema.themes.content;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.content.model.MovieInputDto;
import com.cinema.cinema.themes.content.service.MovieService;
import com.cinema.cinema.themes.user.service.StandardUserService;
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
@Tag(name = "Movies")
@RequestMapping("/${app.prefix}/${app.version}/movies")
public class MovieController {

    private final MovieService movieService;
    private final StandardUserService userService;
    private final DtoMapperService mapperService;


    @GetMapping
    @Operation(summary = "Get all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with movies returned"),
            @ApiResponse(responseCode = "204", description = "No movie found")})
    public ResponseEntity<List<MovieOutputDto>> getAllMovies() {
        List<Movie> movies = movieService.getAllContents();
        List<MovieOutputDto> moviesDto = movies.stream()
                .map(mapperService::mapToMovieDto)
                .toList();
        HttpStatus status = moviesDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(moviesDto, status);
    }

    @GetMapping("/{movieId}")
    @Operation(summary = "Get movie by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie returned"),
            @ApiResponse(responseCode = "404", description = "Movie not found")})
    public ResponseEntity<MovieOutputDto> getMovie(@PathVariable long movieId) {
        Movie movie = movieService.getContent(movieId);
        MovieOutputDto movieDto = mapperService.mapToMovieDto(movie);
        return new ResponseEntity<>(movieDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Movie created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addMovie(@RequestBody MovieInputDto movieDto) {
        Movie movie = mapperService.mapToMovie(movieDto);
        movie = movieService.addContent(movie);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{movieId}")
                .buildAndExpand(movie.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{movieId}")
    @Operation(summary = "Update movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie not found")})
    public ResponseEntity<Void> editMovie(long movieId, @RequestBody MovieInputDto movieDto) {
        Movie movie = mapperService.mapToMovie(movieDto);
        movieService.editContent(movieId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{movieId}/rate/{userId}")
    @Operation(summary = "Rate movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie rated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> rateMovie(@PathVariable long movieId, @PathVariable int userId, @RequestParam int rate) {
        Movie movie = movieService.getContent(movieId);
        userService.rateMovie(userId, movie, rate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{movieId}/watch-list/{userId}")
    @Operation(summary = "Add movie to watch list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to user's watch list"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> addMovieToWatchList(@PathVariable long userId, @PathVariable long movieId) {
        Movie movie = movieService.getContent(movieId);
        userService.addMovieToWatchlist(userId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{movieId}/watch-list/{userId}")
    @Operation(summary = "Remove movie from watch list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie removed from user's watch list"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> removeMovieFromWatchList(@PathVariable long userId, @PathVariable long movieId) {
        Movie movie = movieService.getContent(movieId);
        userService.removeMovieFromWatchlist(userId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
