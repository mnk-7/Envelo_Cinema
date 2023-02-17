package com.cinema.cinema.themes.user.service;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.service.MovieService;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.model.StandardUserInputDto;
import com.cinema.cinema.themes.user.model.StandardUserOutputDto;
import com.cinema.cinema.themes.user.repository.StandardUserRepository;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class StandardUserService extends UserService<StandardUserOutputDto, StandardUserInputDto> {

    private final StandardUserRepository userRepository;
    private final StandardUserValidator userValidator;
    private final MovieService movieService;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public List<StandardUserOutputDto> getAllUsers() {
        List<StandardUser> users = userRepository.findAll();
        return users.stream()
                .map(mapperService::mapToStandardUserDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public StandardUserOutputDto getUser(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        return mapperService.mapToStandardUserDto(user);
    }

    @Override
    @Transactional
    public void editUser(long id, StandardUserInputDto userDto) {
        StandardUser user = userValidator.validateExists(id);
        StandardUser userFromDto = mapperService.mapToStandardUser(userDto);
        userValidator.validateInput(userFromDto);
        setFields(user, userFromDto);
        userRepository.save(user);
    }

    @Transactional
    public StandardUserOutputDto editIsActive(long userId, boolean isActive) {
        StandardUser user = userValidator.validateExists(userId);
        userValidator.validateIsActiveChange(user, isActive);
        user.setActive(isActive);
        user = userRepository.save(user);
        return mapperService.mapToStandardUserDto(user);
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
        user.getOrders().remove(order);
    }

    //TODO cart
//    public Cart getCart(long id) {
//        StandardUser user = getStandardUser(id);
//        return user.getCart();
//    }

    //TODO invoice
//    public List<Invoice> getAllInvoices(long id) {
//        StandardUser user = getStandardUser(id);
//        return user.getOrders().stream().map(Order::getInvoice).toList();
//    }

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
