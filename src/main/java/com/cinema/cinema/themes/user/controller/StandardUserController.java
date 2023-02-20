package com.cinema.cinema.themes.user.controller;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.cart.model.Cart;
import com.cinema.cinema.themes.cart.model.CartOutputDto;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.service.MovieService;
import com.cinema.cinema.themes.content.model.MovieOutputDto;
import com.cinema.cinema.themes.order.OrderService;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.order.model.OrderShortDto;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.model.StandardUserOutputDto;
import com.cinema.cinema.themes.user.model.StandardUserInputDto;
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

import java.util.List;

@AllArgsConstructor
@RestController
@Tag(name = "Users")
@RequestMapping("/${app.prefix}/${app.version}/users")
public class StandardUserController {

    private final StandardUserService userService;
    private final MovieService movieService;
    private final OrderService orderService;
    private final CartService cartService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with users returned"),
            @ApiResponse(responseCode = "204", description = "No user found")})
    public ResponseEntity<List<StandardUserOutputDto>> getAllUsers() {
        List<StandardUser> users = userService.getAllUsers();
        List<StandardUserOutputDto> usersDto = users.stream()
                .map(mapperService::mapToStandardUserDto)
                .toList();
        HttpStatus status = usersDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(usersDto, status);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<StandardUserOutputDto> getUser(@PathVariable long userId) {
        StandardUser user = userService.getUser(userId);
        StandardUserOutputDto userDto = mapperService.mapToStandardUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "400", description = "Wrong data"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<Void> editUser(@PathVariable long userId, @RequestBody StandardUserInputDto userDto) {
        StandardUser user = mapperService.mapToStandardUser(userDto);
        userService.editUser(userId, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{userId}/watch-list")
    @Operation(summary = "Get movies to watch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with movies to watch returned"),
            @ApiResponse(responseCode = "204", description = "No movie to watch found")})
    public ResponseEntity<List<MovieOutputDto>> getMoviesToWatch(@PathVariable long userId) {
        List<Movie> moviesToWatch = movieService.getMoviesToWatch(userId);
        List<MovieOutputDto> moviesToWatchDto = moviesToWatch.stream()
                .map(mapperService::mapToMovieDto)
                .toList();
        HttpStatus status = moviesToWatchDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(moviesToWatchDto, status);
    }

    @GetMapping("/{userId}/orders")
    @Operation(summary = "Get orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with orders returned"),
            @ApiResponse(responseCode = "204", description = "No order to watch found")})
    public ResponseEntity<List<OrderShortDto>> getOrders(@PathVariable long userId) {
        List<Order> orders = orderService.getAllOrders(userId);
        List<OrderShortDto> ordersDto = orders.stream()
                .map(mapperService::mapToOrderShortDto)
                .toList();
        HttpStatus status = ordersDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ordersDto, status);
    }

    @GetMapping("/{userId}/cart")
    @Operation(summary = "Get user's cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cart returned"),
            @ApiResponse(responseCode = "404", description = "Cart not found")})
    public ResponseEntity<CartOutputDto> getCart(@PathVariable long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        CartOutputDto cartDto = mapperService.mapToCartDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

}
