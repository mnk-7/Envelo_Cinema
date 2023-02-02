package com.cinema.cinema.services;

import com.cinema.cinema.models.Genre;
import com.cinema.cinema.repositories.GenreRepository;
import com.cinema.cinema.exceptions.GenreException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GenreService {

    private GenreRepository genreRepository;

    public Genre getGenre(long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            throw new GenreException("Genre with given ID not found");
        }
        return genre.get();
    }

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public void addGenre(String name) {
        Genre genre = new Genre();
        genre.setName(name);
        genreRepository.save(genre);
    }

    public void editGenre(long id, String name) {
        Genre genre = getGenre(id);
        genre.setName(name);
        genreRepository.save(genre);
    }

}
