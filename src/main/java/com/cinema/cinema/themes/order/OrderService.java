package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.couponCode.CouponCode;
import com.cinema.cinema.themes.ticket.Ticket;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.model.User;
import com.cinema.cinema.themes.user.service.StandardUserService;
import com.cinema.cinema.themes.ticket.TicketService;
import com.cinema.cinema.themes.invoice.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StandardUserService userService;
    private final CartService cartService;
    private final TicketService ticketService;

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
