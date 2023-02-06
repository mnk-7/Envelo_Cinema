package com.cinema.cinema.themes.genre;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementFoundException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.exceptions.ElementNotModifiedException;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreDtoRead;
import com.cinema.cinema.themes.genre.model.GenreDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final DtoMapperService mapperService;
    private final Validator validator;

    @Transactional(readOnly = true)
    public List<GenreDtoRead> getAllGenres() {
        List<Genre> genres = genreRepository.findAll(Sort.by("name"));
        return genres.stream()
                .map(mapperService::mapToGenreDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GenreDtoRead getGenreById(long id) {
        Genre genre = validateGenreExists(id);
        return mapperService.mapToGenreDto(genre);
    }

    @Transactional
    public GenreDtoRead addGenre(GenreDtoWrite genreDto) {
        validateGenreNotExists(genreDto);
        Genre genre = mapperService.mapToGenre(genreDto);
        validateInputForGenre(genre);
        genre = genreRepository.save(genre);
        return mapperService.mapToGenreDto(genre);
    }

    @Transactional
    public void editGenre(long id, GenreDtoWrite genreDto) {
        Genre genre = validateGenreExists(id);
        Genre genreFromDto = mapperService.mapToGenre(genreDto);
        validateInputForGenre(genreFromDto);
        validateGenreChanged(genre, genreFromDto);
        validateGenreNotExists(genreDto);
        genreFromDto.setId(genre.getId());
        genreRepository.save(genreFromDto);
    }

    private void validateInputForGenre(Genre genre) {
        Set<ConstraintViolation<Genre>> violations = validator.validate(genre);
        if (!violations.isEmpty()) {
            Map<String, String> messages = new HashMap<>();
            for (ConstraintViolation<Genre> violation : violations) {
                messages.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ArgumentNotValidException("Not valid data provided", messages);
        }
    }

    private Genre validateGenreExists(long id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isEmpty()) {
            throw new ElementNotFoundException("Genre with ID " + id + " not found");
        }
        return genre.get();
    }

    private void validateGenreNotExists(GenreDtoWrite genreDto) {
        Optional<Genre> genre = genreRepository.findByName(genreDto.getName());
        if (genre.isPresent()) {
            throw new ElementFoundException("Genre with name " + genre.get().getName() + " already exists");
        }
    }

    private void validateGenreChanged(Genre genre, Genre genreFromDto) {
        if (genre.equals(genreFromDto)) {
            throw new ElementNotModifiedException("No change detected, genre with name " + genre.getName() + " has not been modified");
        }
    }

}
