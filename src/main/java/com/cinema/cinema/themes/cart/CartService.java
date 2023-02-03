package com.cinema.cinema.themes.cart;

import com.cinema.cinema.themes.ticket.Ticket;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class CartService {

    private final CartRepository cartRepository;

    public Cart getCart(long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (cart.isEmpty()) {
            throw new CartException("Cart with given ID not found");
        }
        return cart.get();
    }

    //TODO - moving the methods below to the user? Using user as a parameter instead of cart id?
    public void addTicket(long id, Ticket ticket) {
        Cart cart = getCart(id);
        cart.getTickets().add(ticket);
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    public void removeTicket(long id, Ticket ticket) {
        Cart cart = getCart(id);
        cart.getTickets().remove(ticket);
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    public void clearCart(long id) {
        Cart cart = getCart(id);
        cart.getTickets().clear();
        cart.setLastUpdate(LocalDateTime.now());
        cartRepository.save(cart);
    }

    public Set<Ticket> getAllTickets(long id) {
        Cart cart = getCart(id);
        return cart.getTickets();
    }

}
