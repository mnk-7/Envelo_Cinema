package com.cinema.cinema.repositories;

import com.cinema.cinema.models.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class OrderRepository {

    public Optional<Order> findOrderById(long id) {
        return Optional.of(new Order());
    }

    public List<Order> findAllOrders() {
        return new ArrayList<>();
    }

    public void create(Order order) {
    }

    public void update(long id, Order order) {
    }

    public void delete(long id) {
    }

}
