package com.cinema.cinema.themes.order;

import com.cinema.cinema.themes.cart.CartService;
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
    public OrderShortDto addOrder(OrderInputDto orderDto) {
        Order orderFromDto = mapperService.mapToOrder(orderDto);
        Order order = createOrder(orderFromDto);
        orderValidator.validateInput(order);
        order = orderRepository.save(order);
        ticketService.addOrder(order, order.getTickets());
        //TODO cart
        if (order.getUser() != null) {
            userService.addOrder(order.getUser().getId(), order);
            //cartService.clearCart(user.getCart().getId());
        }
        return mapperService.mapToOrderShortDto(order);
    }

    private Order createOrder(Order orderFromDto ) {
        Order order = new Order();
        setFields(order, orderFromDto);
        order.setCreatedAt(LocalDateTime.now());
        order.setQrCode(QrCodeGenerator.generateQr());
        return order;
    }

    private void setFields(Order order, Order orderFromDto) {
        if (orderFromDto.getUser() != null) {
            StandardUser user = userValidator.validateExists(orderFromDto.getUser().getId());
            order.setUser(user);
        }

        order.setPhone(orderFromDto.getPhone());
        order.setCouponCode(orderFromDto.getCouponCode());
        order.setFirstName(orderFromDto.getFirstName());
        order.setLastName(orderFromDto.getLastName());
        order.setEmail(orderFromDto.getEmail());

        Set<Ticket> ticketsFromDto = orderFromDto.getTickets();
        if (ticketsFromDto != null) {
            for (Ticket ticketFromDto : ticketsFromDto) {
                Ticket ticket = ticketValidator.validateExists(ticketFromDto.getId());
                ticketValidator.validateNotInOrder(ticket);
                order.getTickets().add(ticket);
            }
            order.setPrice(calculatePrice(order));
        }
    }

    private BigDecimal calculatePrice(Order order) {
        return order.getTickets().stream()
                .map(ticket -> ticket.getType().getPrice())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.valueOf(0));
    }

//    public void cancelOrder(long orderId) {
//        //TODO przeanalizować ticket cancellation i order cancellation
//        Order order = orderValidator.validateExists(orderId);
//        if (order.getUser() != null) {
//            userService.removeOrder(order.getUser().getId(), order);
//        }
//        //TODO zwrot pieniędzy
//        if (!calculateTicketRefund(order).equals(BigDecimal.valueOf(0))) {
//        }
//        ticketService.removeOrder(order);
//        //TODO sprawdzic, czy trzeba zerować invoice, coupon code
//        //TODO usunięcie ticketów, zwolnienie miejsc
//        orderRepository.deleteById(orderId);
//    }

    //TODO invoice
//    public void addInvoice(long id, Invoice invoice) {
//        Order order = getOrder(id);
//        order.setInvoice(invoice);
//        orderRepository.save(order);
//    }

//    private BigDecimal calculateTicketRefund(Order order) {
//        return order.getTickets().stream()
//                .map(ticket -> ticket.isPaid() ? ticket.getType().getPrice() : BigDecimal.valueOf(0))
//                .reduce(BigDecimal::add)
//                .orElse(BigDecimal.valueOf(0));
//    }

}
