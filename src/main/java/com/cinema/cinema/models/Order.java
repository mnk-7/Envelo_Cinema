package com.cinema.cinema.models;

import com.cinema.cinema.models.user.StandardUser;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

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
    private int phone;

    @OneToMany(mappedBy = "order")
    private Set<Ticket> tickets;

    @OneToOne(mappedBy = "order")
    private CouponCode couponCode;

    private String qrCode;

    @OneToOne(mappedBy = "order")
    private Invoice invoice;

}
