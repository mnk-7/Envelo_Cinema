package com.cinema.cinema.themes.order;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderValidator extends ValidatorService<Order> {

    private final OrderRepository orderRepository;

    public OrderValidator(Validator validator, OrderRepository orderRepository) {
        super(validator);
        this.orderRepository = orderRepository;
    }

    @Override
    public Order validateExists(long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isEmpty()) {
            throw new ElementNotFoundException("Order with ID " + orderId + " not found");
        }
        return order.get();
    }

}
