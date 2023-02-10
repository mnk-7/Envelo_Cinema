package com.cinema.cinema.themes.user.controller;

import com.cinema.cinema.themes.content.MovieService;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieDtoRead;
import com.cinema.cinema.themes.user.model.StandardUserDtoRead;
import com.cinema.cinema.themes.user.model.StandardUserDtoWrite;
import com.cinema.cinema.themes.user.service.StandardUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Users")
@RequestMapping("/${app.prefix}/${app.version}/users")
public class StandardUserController {

    private final StandardUserService userService;
    private final MovieService movieService;

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with users returned"),
            @ApiResponse(responseCode = "204", description = "No user found")})
    public ResponseEntity<List<StandardUserDtoRead>> getAllUsers() {
        List<StandardUserDtoRead> users = userService.getAllUsers();
        HttpStatus status = users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(users, status);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<StandardUserDtoRead> getUser(@PathVariable long userId) {
        StandardUserDtoRead user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<Void> editAdmin(@PathVariable long userId, @RequestBody StandardUserDtoWrite user) {
        userService.editUser(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/watch-list")
    @Operation(summary = "Get movies to watch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with movies to watch returned"),
            @ApiResponse(responseCode = "204", description = "No movie to watch found")})
    public ResponseEntity<List<MovieDtoRead>> getMoviesToWatch(@PathVariable long userId) {
        List<MovieDtoRead> moviesToWatch = userService.getMoviesToWatch(userId);
        HttpStatus status = moviesToWatch.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(moviesToWatch, status);
    }

    //TODO - to be moved to MovieController after performing tests
    @PostMapping("/{movieId}/watch-list/{userId}")
    @Operation(summary = "Add movie to watch list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie added to user's watch list"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> addMovieToWatchList(@PathVariable long userId, @PathVariable long movieId) {
        Movie movie = movieService.getMovie(movieId);
        userService.addMovieToWatchlist(userId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO - to be moved to MovieController after performing tests
    @DeleteMapping("/{movieId}/watch-list/{userId}")
    @Operation(summary = "Remove movie from watch list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie removed from user's watch list"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> removeMovieFromWatchList(@PathVariable long userId, @PathVariable long movieId) {
        Movie movie = movieService.getMovie(movieId);
        userService.removeMovieFromWatchlist(userId, movie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO - to be moved to MovieController after performing tests
    @PutMapping("/{movieId}/rate/{userId}")
    @Operation(summary = "Rate movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Movie rated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "Movie or user not found")})
    public ResponseEntity<Void> rateMovie(@PathVariable long movieId, @PathVariable int userId, @RequestParam int rate) {
        Movie movie = movieService.getMovie(movieId);
        userService.rateMovie(userId, movie, rate);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
