package com.cinema.cinema.themes.user.model;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.cart.model.Cart;
import com.cinema.cinema.themes.order.model.Order;

import jakarta.validation.constraints.NotNull;
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
@Table(name = "users")
public class StandardUser extends User {

    @NotNull(message = "Field is mandatory")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movies_to_watch_by_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> moviesToWatch = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "movies_rated_by_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> ratedMovies = new HashSet<>();

}
