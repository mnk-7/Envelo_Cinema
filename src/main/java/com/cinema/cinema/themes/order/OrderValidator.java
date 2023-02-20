package com.cinema.cinema.themes.order;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public void validateCancellation(Order order) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        order.getTickets().forEach(ticket -> {
            if (ticket.getShow().getStartDateTime().isBefore(currentDateTime)) {
                throw new ProcessingException("You cannot cancel the order, the show is already in the past");
            }
        });
    }

    public void validateNoUser(Order order) {
        if (order.getUser() != null) {
            throw new ProcessingException("Log in to cancel the order");
        }
    }

}
