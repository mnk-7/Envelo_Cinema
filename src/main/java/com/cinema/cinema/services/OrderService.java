package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.OrderException;
import com.cinema.cinema.models.CouponCode;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.models.Ticket;
import com.cinema.cinema.models.user.User;
import com.cinema.cinema.repositories.OrderRepository;
import com.cinema.cinema.utils.QrCodeGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void addOrder(User user, String firstName, String lastName, String email, int phone, Set<Ticket> tickets, CouponCode couponCode){
        Order order = new Order();
        order.setUser(user);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setTickets(tickets);
        order.setCouponCode(couponCode);
        order.setQrCode(QrCodeGenerator.generateQr());
        orderRepository.create(order);
    }

    public void cancelOrder(long id) {
        orderRepository.delete(id);
    }

}
