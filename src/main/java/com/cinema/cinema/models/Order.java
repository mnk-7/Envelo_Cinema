package com.cinema.cinema.models;

import com.cinema.cinema.models.user.User;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Order {

    private Long id;
    private User user; //data below needed because some user can make order for different data than his account has
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private Set<Ticket> tickets;
    private CouponCode couponCode;
    private String qrCode;

}
