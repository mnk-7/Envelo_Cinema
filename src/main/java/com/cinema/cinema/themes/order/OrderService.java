package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.cart.CartService;
import com.cinema.cinema.themes.cart.CartValidator;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.order.model.OrderInputDto;
import com.cinema.cinema.themes.order.model.OrderOutputDto;
import com.cinema.cinema.themes.order.model.OrderShortDto;
import com.cinema.cinema.themes.ticket.TicketService;
import com.cinema.cinema.themes.ticket.TicketValidator;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.service.StandardUserService;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import com.cinema.cinema.utils.DtoMapperService;
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
    private final OrderValidator orderValidator;
    private final StandardUserValidator userValidator;
    private final TicketValidator ticketValidator;
    private final CartValidator cartValidator;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public OrderOutputDto getOrder(long orderId) {
        Order order = orderValidator.validateExists(orderId);
        order.setPrice(calculatePrice(order));
        return mapperService.mapToOrderDto(order);
    }

    @Transactional(readOnly = true)
    public List<OrderShortDto> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .peek(order -> order.setPrice(calculatePrice(order)))
                .map(mapperService::mapToOrderShortDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderShortDto> getAllOrders(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        Set<Order> orders = user.getOrders();
        return orders.stream()
                .peek(order -> order.setPrice(calculatePrice(order)))
                .map(mapperService::mapToOrderShortDto)
                .toList();
    }

    @Transactional
    public OrderShortDto addOrder(Long userId, OrderInputDto orderDto) {
        StandardUser user = null;
        if (userId != null) {
            user = userValidator.validateExists(userId);
        }
        Order orderFromDto = mapperService.mapToOrder(orderDto);
        Order order = createOrder(user, orderFromDto);
        orderValidator.validateInput(order);
        order = orderRepository.save(order);
        ticketService.addOrder(order, order.getTickets());
        if (user != null) {
            userService.addOrder(user.getId(), order);
            cartService.clearCart(user.getCart().getId());
        }
        return mapperService.mapToOrderShortDto(order);
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
        order.setPhone(orderFromDto.getPhone());
        order.setCouponCode(orderFromDto.getCouponCode());
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
                    ticket.setPaid(true);
                    order.getTickets().add(ticket);
                }
            }
        } else {
            for (Ticket ticketFromCart : user.getCart().getTickets()) {
                ticketValidator.validateNotInOrder(ticketFromCart);
                ticketFromCart.setPaid(true);
                order.getTickets().add(ticketFromCart);
            }
        }
        order.setPrice(calculatePrice(order));
    }

    private BigDecimal calculatePrice(Order order) {
        return order.getTickets().stream()
                .map(ticket -> ticket.getTicketType().getPrice())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

//    public void cancelOrder(long orderId) {
//        Order order = orderValidator.validateExists(orderId);
//        //orderValidator.validateCancellation(order); //żaden seans nie może być w przeszłości
//        if (order.getUser() != null) {
//            userService.removeOrder(order.getUser().getId(), order);
//        }
//        //TODO zwrot pieniędzy -> zniżkowy coupon code
//        if (!calculateTicketRefund(order).equals(BigDecimal.valueOf(0))) {
//
//        }
//        ticketService.removeOrder(order);
//        //TODO sprawdzić, czy trzeba zerować invoice, coupon code
//        //TODO usunięcie ticketów, zwolnienie miejsc
//        orderRepository.deleteById(orderId);
//    }

    //TODO invoice
//    public void addInvoice(long id, Invoice invoice) {
//        Order order = getOrder(id);
//        order.setInvoice(invoice);
//        orderRepository.save(order);
//    }

    private BigDecimal calculateTicketRefund(Order order) {
        return order.getTickets().stream()
                .map(ticket -> ticket.isPaid() ? ticket.getTicketType().getPrice() : BigDecimal.valueOf(0))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

}
