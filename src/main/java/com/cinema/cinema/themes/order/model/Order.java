package com.cinema.cinema.themes.order.model;

import com.cinema.cinema.themes.couponCode.model.CouponCode;
import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.ticket.model.Ticket;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private StandardUser user; //data below needed because some user can make order for different data than his account has

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "order")
    private Set<Ticket> tickets = new HashSet<>();

    @OneToOne(mappedBy = "order")
    private CouponCode couponCode;

    private String qrCode;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

}
