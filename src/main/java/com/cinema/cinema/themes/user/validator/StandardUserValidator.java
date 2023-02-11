package com.cinema.cinema.themes.user.validator;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ProcessingException;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.repository.StandardUserRepository;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StandardUserValidator extends ValidatorService<StandardUser> {

    private final StandardUserRepository standardUserRepository;

    public StandardUserValidator(Validator validator, StandardUserRepository standardUserRepository) {
        super(validator);
        this.standardUserRepository = standardUserRepository;
    }

    @Override
    public StandardUser validateExists(long userId) {
        Optional<StandardUser> user = standardUserRepository.findById(userId);
        if (user.isEmpty()) {
            throw new ElementNotFoundException("User user with ID " + userId + " not found");
        }
        return user.get();
    }

    public void validateIsActiveChange(StandardUser user, boolean isActive) {
        if (isActive && user.isActive()) {
            throw new ProcessingException("User with id " + user.getId() + " is already active");
        }
        if (!isActive && !user.isActive()) {
            throw new ProcessingException("User with id " + user.getId() + " is already not active");
        }
    }

    public void validateMovieNotRated(StandardUser user, Movie movie) {
        if (user.getRatedMovies().contains(movie)) {
            throw new ProcessingException("Movie " + movie.getTitle() + " has already been rated by user with id " + user.getId());
        }
    }

    public void validateMovieNotInWatchList(StandardUser user, Movie movie) {
        if (user.getMoviesToWatch().contains(movie)) {
            throw new ProcessingException("Movie " + movie.getTitle() + " has already been added to watch list of the user with id " + user.getId());
        }
    }

    public void validateMovieInWatchList(StandardUser user, Movie movie) {
        if (!user.getMoviesToWatch().contains(movie)) {
            throw new ProcessingException("Movie " + movie.getTitle() + " has not been found in the watch list of the user with id " + user.getId());
        }
    }

}
