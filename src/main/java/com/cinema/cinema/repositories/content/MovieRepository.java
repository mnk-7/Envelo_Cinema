package com.cinema.cinema.repositories.content;

import com.cinema.cinema.models.content.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class MovieRepository {

    public Optional<Movie> findMovieById(long id) {
        return Optional.of(new Movie());
    }

    public List<Movie> findAllMovies() {
        return new ArrayList<>();
    }

    public void create(Movie movie) {
    }

    public void update(long id, Movie movie) {
    }

}
