package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.order.model.OrderInputDto;
import com.cinema.cinema.themes.order.model.OrderOutputDto;
import com.cinema.cinema.themes.order.model.OrderShortDto;
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
@Tag(name = "Orders")
@RequestMapping("/${app.prefix}/${app.version}/orders")
public class OrderController {

    private final OrderService orderService;
    private final DtoMapperService mapperService;

    @GetMapping
    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List with orders returned"),
            @ApiResponse(responseCode = "204", description = "No order found")})
    public ResponseEntity<List<OrderShortDto>> getAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        List<OrderShortDto> ordersDto = orders.stream()
                .map(mapperService::mapToOrderShortDto)
                .toList();
        HttpStatus status = ordersDto.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
        return new ResponseEntity<>(ordersDto, status);
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order returned"),
            @ApiResponse(responseCode = "404", description = "Order not found")})
    public ResponseEntity<OrderOutputDto> getOrder(@PathVariable long orderId) {
        Order order = orderService.getOrder(orderId);
        OrderOutputDto orderDto = mapperService.mapToOrderDto(order);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created"),
            @ApiResponse(responseCode = "400", description = "Wrong data")})
    public ResponseEntity<Void> addOrder(@RequestParam(required = false) Long userId, @RequestBody OrderInputDto orderDto) {
        Order order = mapperService.mapToOrder(orderDto);
        order = orderService.addOrder(userId, order);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{orderId}")
                .buildAndExpand(order.getId())
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
