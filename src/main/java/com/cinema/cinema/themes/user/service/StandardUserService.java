package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.service.MovieService;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.repository.StandardUserRepository;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class StandardUserService extends UserService<StandardUser> {

    private final StandardUserRepository userRepository;
    private final StandardUserValidator userValidator;
    private final MovieService movieService;

    @Transactional(readOnly = true)
    public List<StandardUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public StandardUser getUser(long userId) {
        return userValidator.validateExists(userId);
    }

    @Override
    @Transactional
    public void editUser(long id, StandardUser userFromDto) {
        StandardUser user = userValidator.validateExists(id);
        userValidator.validateInput(userFromDto);
        setFields(user, userFromDto);
        userRepository.save(user);
    }

    @Transactional
    public StandardUser editIsActive(long userId, boolean isActive) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateIsActiveChange(user, isActive);
        user.setActive(isActive);
        return userRepository.save(user);
    }

    @Transactional
    public String generateNewPassword(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        String password = PasswordGenerator.generatePassword(user);
        editPassword(user, password);
        return password;
    }

    @Transactional
    public void addMovieToWatchlist(long userId, Movie movie) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateMovieNotInWatchList(user, movie);
        user.getMoviesToWatch().add(movie);
        userRepository.save(user);
    }

    @Transactional
    public void removeMovieFromWatchlist(long userId, Movie movie) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateMovieInWatchList(user, movie);
        user.getMoviesToWatch().remove(movie);
        userRepository.save(user);
    }

    @Transactional
    public void rateMovie(long userId, Movie movie, int rate) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateMovieNotRated(user, movie);
        user.getRatedMovies().add(movie);
        movieService.rateMovie(movie, rate);
    }

    @Transactional
    public void addOrder(long userId, Order order) {
        StandardUser user = userValidator.validateExists(userId);
        user.getOrders().add(order);
    }

    @Transactional
    public void removeOrder(long userId, Order order) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateOrderInOrderList(user, order);
        user.getOrders().remove(order);
    }

    private void setFields(StandardUser user, StandardUser userFromDto) {
        user.setFirstName(userFromDto.getFirstName());
        user.setLastName(userFromDto.getLastName());
        user.setEmail(userFromDto.getEmail());
        user.setPassword(userFromDto.getPassword());
        user.setPhone(userFromDto.getPhone());
    }

    private void editPassword(StandardUser user, String password) {
        user.setPassword(password);
        userRepository.save(user);
    }

}
