package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.GenreException;
import com.cinema.cinema.models.categories.Genre;
import com.cinema.cinema.models.content.Movie;
import com.cinema.cinema.repositories.GenreRepository;

import java.util.List;
import java.util.Optional;

public class GenreService {

    private GenreRepository genreRepository;

    public Genre getGenre(long id) {
        Optional<Genre> genre = genreRepository.findGenreById();
        if (genre.isEmpty()) {
            throw new GenreException("Genre with given ID not found");
        }
        return genre.get();
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAllGenres();
    }

    public List<Genre> getAllGenreForMovie(Movie movie) {
        return genreRepository.findAllGenresByMovie(movie);
    }

    public void addGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.create(genre);
    }

    public void editGenre(long id, String name) {
        Genre genre = getGenre(id);
        genre.setName(name);
        genreRepository.update(id, genre);
    }

}
