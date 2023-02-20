package com.cinema.cinema.themes.cart;

import com.cinema.cinema.themes.cart.model.Cart;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartValidator cartValidator;
    private final StandardUserValidator userValidator;

    @Transactional(readOnly = true)
    public Cart getCartByUserId(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        return user.getCart();
    }

    @Transactional(readOnly = true)
    public Cart getCartByCartId(long cartId) {
        return cartValidator.validateExists(cartId);
    }

    @Transactional
    public void addTicket(long cartId, Ticket ticket) {
        Cart cart = cartValidator.validateExists(cartId);
        cart.getTickets().add(ticket);
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public void removeTicket(long cartId, Ticket ticket) {
        Cart cart = cartValidator.validateExists(cartId);
        cart.getTickets().remove(ticket);
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(long cartId) {
        Cart cart = cartValidator.validateExists(cartId);
        cart.getTickets().clear();
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

}
