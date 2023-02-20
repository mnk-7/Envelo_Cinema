package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.order.model.OrderInputDto;
import com.cinema.cinema.themes.order.model.OrderOutputDto;
import com.cinema.cinema.themes.order.model.OrderShortDto;
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
@Tag(name = "Orders")
@RequestMapping("/${app.prefix}/${app.version}/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with orders returned"),
            @ApiResponse(responseCode = "204", description = "No order found")})
    public ResponseEntity<List<OrderShortDto>> getAllOrders() {
        List<OrderShortDto> orders = orderService.getAllOrders();
        HttpStatus status = orders.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(orders, status);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order returned"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    public ResponseEntity<OrderOutputDto> getOrder(@PathVariable long orderId) {
        OrderOutputDto order = orderService.getOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addOrder(@RequestParam(required = false) Long userId, @RequestBody OrderInputDto order) {
        OrderShortDto orderCreated = orderService.addOrder(userId, order);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(orderCreated.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{orderId}")
    @Operation(summary = "Delete order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order deleted"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    public ResponseEntity<Void> cancelOrder(@RequestParam(required = false) Long userId, @PathVariable long orderId) {
        orderService.cancelOrder(userId, orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
