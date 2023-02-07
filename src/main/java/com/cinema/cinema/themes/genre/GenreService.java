package com.cinema.cinema.themes.genre;

import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.genre.model.GenreDtoRead;
import com.cinema.cinema.themes.genre.model.GenreDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class GenreService {

    private final GenreRepository repository;
    private final GenreValidator validator;
    private final DtoMapperService mapper;

    @Transactional(readOnly = true)
    public List<GenreDtoRead> getAllGenres() {
        List<Genre> genres = repository.findAll(Sort.by("name"));
        return genres.stream()
                .map(mapper::mapToGenreDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GenreDtoRead getGenreById(long id) {
        Genre genre = validator.validateExists(id);
        return mapper.mapToGenreDto(genre);
    }

    @Transactional
    public GenreDtoRead addGenre(GenreDtoWrite genreDto) {
        Genre genre = mapper.mapToGenre(genreDto);
        validator.validateNotExists(genre);
        validator.validateInput(genre);
        genre = repository.save(genre);
        return mapper.mapToGenreDto(genre);
    }

    @Transactional
    public void editGenre(long id, GenreDtoWrite genreDto) {
        Genre genre = validator.validateExists(id);
        Genre genreFromDto = mapper.mapToGenre(genreDto);
        validator.validateInput(genreFromDto);
        validator.validateChanged(genre, genreFromDto);
        validator.validateNotExists(genreFromDto);
        genreFromDto.setId(genre.getId());
        repository.save(genreFromDto);
    }

}
