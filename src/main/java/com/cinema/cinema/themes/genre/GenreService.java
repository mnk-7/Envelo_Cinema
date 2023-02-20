package com.cinema.cinema.themes.genre;

import com.cinema.cinema.themes.genre.model.Genre;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreValidator genreValidator;

    @Transactional(readOnly = true)
    public List<Genre> getAllGenres() {
        return genreRepository.findAll(Sort.by("name"));
    }

    @Transactional(readOnly = true)
    public Genre getGenre(long genreId) {
        return genreValidator.validateExists(genreId);
    }

    @Transactional
    public Genre addGenre(Genre genre) {
        genreValidator.validateNotExists(genre);
        genreValidator.validateInput(genre);
        return genreRepository.save(genre);
    }

    @Transactional
    public void editGenre(long genreId, Genre genreFromDto) {
        Genre genre = genreValidator.validateExists(genreId);
        genreValidator.validateInput(genreFromDto);
        genreValidator.validateChanged(genre, genreFromDto);
        genreValidator.validateNotExists(genreFromDto);
        setFields(genre, genreFromDto);
        genreRepository.save(genre);
    }

    private void setFields(Genre genre, Genre genreFromDto) {
        genre.setName(genreFromDto.getName());
    }

}
