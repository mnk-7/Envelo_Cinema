package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.cart.CartValidator;
import com.cinema.cinema.themes.couponCode.CouponCodeService;
import com.cinema.cinema.themes.couponCode.model.CouponCode;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.show.ShowValidator;
import com.cinema.cinema.themes.ticket.TicketService;
import com.cinema.cinema.themes.ticket.TicketValidator;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.service.StandardUserService;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final StandardUserService userService;
    private final CartService cartService;
    private final TicketService ticketService;
    private final CouponCodeService couponCodeService;
    private final OrderValidator orderValidator;
    private final StandardUserValidator userValidator;
    private final TicketValidator ticketValidator;
    private final CartValidator cartValidator;
    private final ShowValidator showValidator;

    @Transactional(readOnly = true)
    public Order getOrder(long orderId) {
        Order order = orderValidator.validateExists(orderId);
        order.setPrice(calculatePrice(order));
        return order;
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .peek(order -> order.setPrice(calculatePrice(order)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Order> getAllOrders(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        Set<Order> orders = user.getOrders();
        return orders.stream()
                .peek(order -> order.setPrice(calculatePrice(order)))
                .toList();
    }

    @Transactional
    public Order addOrder(Long userId, Order orderFromDto) {
        StandardUser user = null;
        if (userId != null) {
            user = userValidator.validateExists(userId);
        }
        Order order = createOrder(user, orderFromDto);
        orderValidator.validateInput(order);
        order = orderRepository.save(order);
        ticketService.addOrder(order, order.getTickets());
        if (user != null) {
            userService.addOrder(user.getId(), order);
            cartService.clearCart(user.getCart().getId());
        }
        return order;
    }

    private Order createOrder(StandardUser user, Order orderFromDto) {
        Order order = new Order();
        setFields(user, order, orderFromDto);
        order.setCreatedAt(LocalDateTime.now());
        order.setQrCode(QrCodeGenerator.generateQr());
        return order;
    }

    private void setFields(StandardUser user, Order order, Order orderFromDto) {
        if (user != null) {
            order.setUser(user);
        }
        if (orderFromDto.getCouponCode() != null) {
            CouponCode couponCode = couponCodeService.useCouponCode(orderFromDto.getCouponCode().getCode(), order);
            order.setCouponCode(couponCode);
        }
        order.setPhone(orderFromDto.getPhone());
        order.setFirstName(orderFromDto.getFirstName());
        order.setLastName(orderFromDto.getLastName());
        order.setEmail(orderFromDto.getEmail());
        setTickets(user, order, orderFromDto);
    }

    private void setTickets(StandardUser user, Order order, Order orderFromDto) {
        //TODO poprawić + dopasować OrderInputDto, by zamiast listy ticketów zawierał cart (po zaimplementowaniu koszyka dla niezalogowanych userów)
        if (user == null) {
            Set<Ticket> ticketsFromDto = orderFromDto.getTickets();
            if (ticketsFromDto != null) {
                for (Ticket ticketFromDto : ticketsFromDto) {
                    Ticket ticket = ticketValidator.validateExists(ticketFromDto.getId());
                    ticketValidator.validateNotInOrder(ticket);
                    cartValidator.validateTicketNotInAnotherCart(ticketFromDto);
                    showValidator.validateInFuture(ticket.getShow());
                    ticket.setPaid(true);
                    order.getTickets().add(ticket);
                }
            }
        } else {
            for (Ticket ticketFromCart : user.getCart().getTickets()) {
                ticketValidator.validateNotInOrder(ticketFromCart);
                showValidator.validateInFuture(ticketFromCart.getShow());
                ticketFromCart.setPaid(true);
                order.getTickets().add(ticketFromCart);
            }
        }
        order.setPrice(calculatePrice(order));
    }

    private BigDecimal calculatePrice(Order order) {
        BigDecimal price = order.getTickets().stream()
                .map(ticket -> ticket.getTicketType().getPrice())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));

        if (!price.equals(BigDecimal.valueOf(0)) && order.getCouponCode() != null) {
            BigDecimal discount = price.multiply(BigDecimal.valueOf(order.getCouponCode().getDiscountPercent() / 100.0));
            price = price.subtract(discount);
        }
        return price;
    }

    public void cancelOrder(Long userId, long orderId) {
        Order order = orderValidator.validateExists(orderId);
        if (userId != null) {
            userService.removeOrder(userId, order);
        } else {
            orderValidator.validateNoUser(order);
        }
        orderValidator.validateCancellation(order);
//        if (!calculatePrice(order).equals(BigDecimal.valueOf(0))) {
//            //TODO zwrot pieniędzy -> zniżkowy coupon code
//        }
        if (order.getCouponCode() != null) {
            couponCodeService.removeOrder(order.getCouponCode());
            order.setCouponCode(null);
        }
//        if (order.getInvoice() != null) {
//            //TODO zerowanie invoice
//        }
        ticketService.removeTicketFromOrder(order);
        orderRepository.deleteById(orderId);
    }

    //TODO invoice
//    public void addInvoice(long id, Invoice invoice) {
//        Order order = getOrder(id);
//        order.setInvoice(invoice);
//        orderRepository.save(order);
//    }

}
