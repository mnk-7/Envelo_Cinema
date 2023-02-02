package com.cinema.cinema.controllers;

import com.cinema.cinema.models.CouponCode;
import com.cinema.cinema.models.Invoice;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.models.user.StandardUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@Tag(name = "Orders")
@RequestMapping("/orders")
public class OrderController {

    private List<Order> orders;

    public OrderController() {
        this.orders = new ArrayList<>();
        orders.add(new Order(1L, new StandardUser(), "Anna", "Nowak", "abc@abc", 789789789, new HashSet<>(), new CouponCode(), "", new Invoice()));
    }

    @Operation(summary = "Find order by id")
    @GetMapping("/{id}")
    public Order getOrders(@PathVariable int id) {
        return orders.get(0);
    }

}
