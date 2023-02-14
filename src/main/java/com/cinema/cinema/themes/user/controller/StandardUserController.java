package com.cinema.cinema.themes.user.controller;

import com.cinema.cinema.themes.content.service.MovieService;
import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.user.model.StandardUserOutputDto;
import com.cinema.cinema.themes.user.model.StandardUserInputDto;
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
    public ResponseEntity<List<StandardUserOutputDto>> getAllUsers() {
        List<StandardUserOutputDto> users = userService.getAllUsers();
        HttpStatus status = users.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(users, status);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<StandardUserOutputDto> getUser(@PathVariable long userId) {
        StandardUserOutputDto user = userService.getUser(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<Void> editAdmin(@PathVariable long userId, @RequestBody StandardUserInputDto user) {
        userService.editUser(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/watch-list")
    @Operation(summary = "Get movies to watch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with movies to watch returned"),
            @ApiResponse(responseCode = "204", description = "No movie to watch found")})
    public ResponseEntity<List<MovieOutputDto>> getMoviesToWatch(@PathVariable long userId) {
        List<MovieOutputDto> moviesToWatch = userService.getMoviesToWatch(userId);
        HttpStatus status = moviesToWatch.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(moviesToWatch, status);
    }



}
