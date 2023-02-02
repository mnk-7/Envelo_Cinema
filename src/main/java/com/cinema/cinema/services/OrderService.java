package com.cinema.cinema.services;

import com.cinema.cinema.models.CouponCode;
import com.cinema.cinema.models.Ticket;
import com.cinema.cinema.models.user.StandardUser;
import com.cinema.cinema.models.user.User;
import com.cinema.cinema.repositories.OrderRepository;
import com.cinema.cinema.services.user.StandardUserService;
import com.cinema.cinema.utils.QrCodeGenerator;
import com.cinema.cinema.exceptions.OrderException;
import com.cinema.cinema.models.Invoice;
import com.cinema.cinema.models.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class OrderService {

    private OrderRepository orderRepository;
    private StandardUserService userService;
    private CartService cartService;
    private TicketService ticketService;

    public Order getOrder(long id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new OrderException("Order with given ID not found");
        }
        return order.get();
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void addOrder(StandardUser user, String firstName, String lastName, String email, int phone, Set<Ticket> tickets, CouponCode couponCode) {
        Order order = new Order();
        order.setUser(user);
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setEmail(email);
        order.setPhone(phone);
        order.setTickets(tickets);
        order.setCouponCode(couponCode);
        order.setQrCode(QrCodeGenerator.generateQr());
        orderRepository.save(order);
        ticketService.editOrder(order, tickets);
        if (user != null) {
            userService.addOrder(user.getId(), order);
            cartService.clearCart(user.getCart().getId());
        }
    }

    public void cancelOrder(long id, User user) {
        Order order = getOrder(id);
        orderRepository.deleteById(id);
        userService.removeOrder(user.getId(), order);
    }

    public void addInvoice(long id, Invoice invoice) {
        Order order = getOrder(id);
        order.setInvoice(invoice);
        orderRepository.save(order);
    }

    public Set<Ticket> getAllTickets(long id) {
        Order order = getOrder(id);
        return order.getTickets();
    }

}
