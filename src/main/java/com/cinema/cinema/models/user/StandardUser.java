package com.cinema.cinema.models.user;

import com.cinema.cinema.models.content.Movie;
import com.cinema.cinema.models.Cart;
import com.cinema.cinema.models.Order;

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
@Table(name = "users")
public class StandardUser extends User {

    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;

    @ManyToMany
    @JoinTable(name = "movies_to_watch_by_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> moviesToWatch;

    @ManyToMany
    @JoinTable(name = "movies_rated_by_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "movie_id"))
    private Set<Movie> ratedMovies;

}
