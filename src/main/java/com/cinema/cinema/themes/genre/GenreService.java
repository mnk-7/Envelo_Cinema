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

    private final GenreRepository genreRepository;
    private final GenreValidator genreValidator;
    private final DtoMapperService mapperService;

    @Transactional(readOnly = true)
    public List<GenreDtoRead> getAllGenres() {
        List<Genre> genres = genreRepository.findAll(Sort.by("name"));
        return genres.stream()
                .map(mapperService::mapToGenreDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GenreDtoRead getGenreById(long genreId) {
        Genre genre = genreValidator.validateExists(genreId);
        return mapperService.mapToGenreDto(genre);
    }

    @Transactional
    public GenreDtoRead addGenre(GenreDtoWrite genreDto) {
        Genre genre = mapperService.mapToGenre(genreDto);
        genreValidator.validateNotExists(genre);
        genreValidator.validateInput(genre);
        genre = genreRepository.save(genre);
        return mapperService.mapToGenreDto(genre);
    }

    @Transactional
    public void editGenre(long genreId, GenreDtoWrite genreDto) {
        Genre genre = genreValidator.validateExists(genreId);
        Genre genreFromDto = mapperService.mapToGenre(genreDto);
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
