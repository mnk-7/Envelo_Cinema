package com.cinema.cinema.models.user;

import com.cinema.cinema.models.Cart;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.models.content.Movie;

import java.util.Set;

public class StandardUser extends User {

    private Integer phone;
    private Boolean isActive;
    private Cart cart;
    private Set<Order> orders;

    private Set<Movie> moviesToWatch;
    private Set<Movie> ratedMovies;

}
