package com.cinema.cinema.model.user;

import com.cinema.cinema.model.Cart;
import com.cinema.cinema.model.Order;
import com.cinema.cinema.model.content.Movie;

import java.util.Set;

public class StandardUser extends User {

    private Integer phone;
    private Boolean isActive;
    private Cart cart;
    private Set<Order> orders;

    private Set<Movie> moviesToWatch;
    private Set<Movie> ratedMovies;

}
