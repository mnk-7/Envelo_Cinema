package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.OrderException;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    public Order getOrder(long id) {
        Optional<Order> order = orderRepository.findOrderById(id);
        if (order.isEmpty()) {
            throw new OrderException("Order with given ID not found");
        }
        return order.get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllOrders();
    }

    public void cancelOrder(long id) {
        orderRepository.delete(id);
    }

}
