package com.cinema.cinema.themes.cart;

import com.cinema.cinema.themes.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(value = """
            SELECT * FROM carts
            WHERE id
            IN (SELECT cart_id FROM carts
            LEFT JOIN tickets_in_carts
            ON carts.id = tickets_in_carts.cart_id
            WHERE ticket_id = ?1)
            """,
            nativeQuery = true)
    List<Cart> findByTicketId(long ticketId);

}
