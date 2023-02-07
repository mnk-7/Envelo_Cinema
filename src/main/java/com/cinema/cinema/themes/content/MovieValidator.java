package com.cinema.cinema.themes.content;

import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;

import java.util.Optional;

public class MovieValidator extends ValidatorService<Movie> {

    private final MovieRepository repository;

    public MovieValidator(Validator validator, MovieRepository repository) {
        super(validator);
        this.repository = repository;
    }

    @Override
    public Movie validateExists(long id) {
        Optional<Movie> movie = repository.findById(id);
        if (movie.isEmpty()) {
            throw new ElementNotFoundException("Movie with ID " + id + " not found");
        }
        return movie.get();
    }

}
