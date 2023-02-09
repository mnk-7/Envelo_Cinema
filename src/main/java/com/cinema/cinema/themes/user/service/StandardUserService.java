package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.user.UserException;
import com.cinema.cinema.themes.cart.model.Cart;
import com.cinema.cinema.themes.invoice.model.Invoice;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.repository.StandardUserRepository;
import com.cinema.cinema.themes.content.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class StandardUserService extends UserService<StandardUser> {

    private final StandardUserRepository userRepository;
    private final MovieService movieService;

    public List<StandardUser> getAllUsers() {
        return userRepository.findAll();
    }

    public StandardUser getStandardUser(long id) {
        return getUser(id);
    }

    @Override
    protected StandardUser getUser(long id) {
        Optional<StandardUser> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserException("User with given ID not found");
        }
        return user.get();
    }

    public void editStandardUser(long id, String firstName, String lastName, String password, String email, int phone) {
        StandardUser user = editUser(id, firstName, lastName, password, email, phone);
        userRepository.save(user);
    }

    @Override
    protected StandardUser editUser(long id, String firstName, String lastName, String password, String email, int phone) {
        StandardUser user = getStandardUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        return user;
    }

    public void editIsActive(long id, boolean isActive) {
        StandardUser user = getStandardUser(id);
        user.setActive(isActive);
        userRepository.save(user);
    }

    public void editPassword(long id, String password) {
        StandardUser user = getStandardUser(id);
        user.setPassword(password);
        userRepository.save(user);
    }

    //TODO - would it be better to pass user instead of id in the methods below and/or above?
    public Set<Movie> getMoviesToWatch(long id) {
        StandardUser user = getStandardUser(id);
        return user.getMoviesToWatch();
    }

    public void addMovieToWatchlist(long id, Movie movie) {
        StandardUser user = getStandardUser(id);
        user.getMoviesToWatch().add(movie);
        userRepository.save(user);
    }

    public void removeMovieFromWatchlist(long id, Movie movie) {
        StandardUser user = getStandardUser(id);
        user.getMoviesToWatch().remove(movie);
        userRepository.save(user);
    }

    @Transactional
    public void rateMovie(long userId, Movie movie, int rate) {
        StandardUser user = getStandardUser(userId);
        if (user.getRatedMovies().contains(movie)) {
            throw new UserException("You have already rated this movie");
        }
        user.getRatedMovies().add(movie);
        userRepository.save(user);
        movieService.rateMovie(movie, rate);
    }

    public Set<Order> getAllOrders(long id) {
        StandardUser user = getStandardUser(id);
        return user.getOrders();
    }

    //TODO - needs to be analyzed
    public Order getOrder(long id, Order order) {
        StandardUser user = getStandardUser(id);
        if (user.getOrders().contains(order)) {
            return order;
        }
        throw new UserException("You don't have such an order");
    }

    public void addOrder(long id, Order order) {
        StandardUser user = getStandardUser(id);
        user.getOrders().add(order);
        userRepository.save(user);
    }

    public void removeOrder(long id, Order order) {
        StandardUser user = getStandardUser(id);
        user.getOrders().remove(order);
        userRepository.save(user);
    }

    public Cart getCart(long id) {
        StandardUser user = getStandardUser(id);
        return user.getCart();
    }

    public List<Invoice> getAllInvoices(long id) {
        StandardUser user = getStandardUser(id);
        return user.getOrders().stream().map(Order::getInvoice).toList();
    }

}
