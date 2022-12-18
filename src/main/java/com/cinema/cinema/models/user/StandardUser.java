package com.cinema.cinema.models.user;

import com.cinema.cinema.models.Cart;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.models.content.Movie;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StandardUser extends User {

    private Boolean isActive;
    private Cart cart;
    private Set<Order> orders;

    private Set<Movie> moviesToWatch;
    private Set<Movie> ratedMovies;

}
