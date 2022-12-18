package com.cinema.cinema.repositories;

import com.cinema.cinema.models.categories.Genre;
import com.cinema.cinema.models.content.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO
@Repository
public class GenreRepository {

    public Optional<Genre> findGenreById() {
        return Optional.of(new Genre());
    }

    public List<Genre> findAllGenres() {
        return new ArrayList<>();
    }

    public List<Genre> findAllGenresByMovie(Movie movie) {
        return new ArrayList<>();
    }

    public void create(Genre genre) {
    }

    public void update(long id, Genre genre) {
    }

}
