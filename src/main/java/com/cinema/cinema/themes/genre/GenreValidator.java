package com.cinema.cinema.themes.genre;

import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GenreValidator extends ValidatorService<Genre> {

    private final GenreRepository genreRepository;

    public GenreValidator(Validator validator, GenreRepository genreRepository) {
        super(validator);
        this.genreRepository = genreRepository;
    }

    @Override
    public Genre validateExists(long genreId) {
        Optional<Genre> genre = genreRepository.findById(genreId);
        if (genre.isEmpty()) {
            throw new ElementNotFoundException("Genre with ID " + genreId + " not found");
        }
        return genre.get();
    }

    public void validateNotExists(Genre genreFromDto) {
        Optional<Genre> genre = genreRepository.findByName(genreFromDto.getName());
        if (genre.isPresent()) {
            throw new ElementFoundException("Genre with name " + genre.get().getName() + " already exists");
        }
    }

    public void validateChanged(Genre genre, Genre genreFromDto) {
        if (genre.equals(genreFromDto)) {
            throw new ElementNotModifiedException("No change detected, genre with name " + genre.getName() + " has not been modified");
        }
    }

}
