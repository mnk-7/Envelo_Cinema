package com.cinema.cinema.themes.content;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieValidator extends ValidatorService<Movie> {

    private final MovieRepository movieRepository;

    public MovieValidator(Validator validator, MovieRepository movieRepository) {
        super(validator);
        this.movieRepository = movieRepository;
    }

    @Override
    public Movie validateExists(long movieId) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            throw new ElementNotFoundException("Movie with ID " + movieId + " not found");
        }
        return movie.get();
    }

    public void validateRate(int rate) {
        if (rate > 5 || rate < 0) {
            throw new ArgumentNotValidException("Rate value " + rate + " is not valid");
        }
    }

}
