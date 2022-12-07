package com.cinema.cinema.model;

import com.cinema.cinema.model.user.User;

import java.util.Set;

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
