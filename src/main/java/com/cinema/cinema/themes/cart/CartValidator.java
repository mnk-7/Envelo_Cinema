package com.cinema.cinema.themes.cart;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.cart.model.Cart;
import com.cinema.cinema.themes.ticket.model.Ticket;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartValidator extends ValidatorService<Cart> {

    private final CartRepository cartRepository;

    public CartValidator(Validator validator, CartRepository cartRepository) {
        super(validator);
        this.cartRepository = cartRepository;
    }

    @Override
    public Cart validateExists(long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (cart.isEmpty()) {
            throw new ElementNotFoundException("Cart with ID " + cartId + " not found");
        }
        return cart.get();
    }

    public void validateTicketNotInCart(Ticket ticket) {
        List<Cart> carts = cartRepository.findByTicketId(ticket.getId());
        if (!carts.isEmpty()) {
            throw new ProcessingException("You need to log in to remove the ticket from the cart");
        }
    }

    public void validateTicketInCart(Cart cart, Ticket ticket) {
        if (!cart.getTickets().contains(ticket)) {
            throw new ProcessingException("Ticket is not in your cart, you cannot remove it");
        }
    }

    public void validateTicketNotInAnotherCart(Ticket ticket) {
        List<Cart> cart = cartRepository.findByTicketId(ticket.getId());
        if (!cart.isEmpty()) {
            throw new ProcessingException("You cannot add the ticket to your order");
        }
    }

}
