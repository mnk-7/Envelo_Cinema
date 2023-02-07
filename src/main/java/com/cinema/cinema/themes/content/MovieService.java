package com.cinema.cinema.themes.content;

import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.model.MovieDtoRead;
import com.cinema.cinema.themes.content.model.MovieDtoWrite;
import com.cinema.cinema.utils.DtoMapperService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class MovieService extends ContentService<MovieDtoRead, MovieDtoWrite> {

    private final MovieRepository repository;
    private final MovieValidator validator;
    private final DtoMapperService mapper;

    @Override
    @Transactional(readOnly = true)
    public List<MovieDtoRead> getAllContents() {
        List<Movie> movies = repository.findAll(Sort.by("title"));
        return movies.stream()
                .peek(movie -> movie.setRating(calculateRating(movie)))
                .map(mapper::mapToMovieDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    protected MovieDtoRead getContent(long id) {
        Movie movie = validator.validateExists(id);
        movie.setRating(calculateRating(movie));
        return mapper.mapToMovieDto(movie);
    }

    @Override
    @Transactional
    public MovieDtoRead addContent(MovieDtoWrite movieDto) {
        Movie movie = mapper.mapToMovie(movieDto);
        validator.validateInput(movie);
        movie = repository.save(movie);
        return mapper.mapToMovieDto(movie);
    }

    @Override
    @Transactional
    public void editContent(long id, MovieDtoWrite movieDto) {
        Movie movie = validator.validateExists(id);
        Movie movieFromDto = mapper.mapToMovie(movieDto);
        validator.validateInput(movieFromDto);
        setFields(movie, movieDto);
        repository.save(movie);
    }

    @Transactional
    public void rateMovie(long id, int rate) {
        Movie movie = validator.validateExists(id);
        movie.setRatingCount(movie.getRatingCount() + 1);
        movie.setRatingSum(movie.getRatingSum() + rate);
        repository.save(movie);
    }

    private void setFields(Movie movie, MovieDtoWrite movieDto) {
        movie.setTitle(movieDto.getTitle());
        movie.setDurationInMinutes(movieDto.getDurationInMinutes());
        movie.setAgeRestriction(movieDto.getAgeRestriction());
        movie.setShortDescription(movieDto.getShortDescription());
        movie.setLongDescription(movieDto.getLongDescription());
        movie.setImageUrl(movieDto.getImageUrl());
        movie.setGenres(movieDto.getGenres());
        movie.setPremiere(movieDto.isPremiere());
    }

    private double calculateRating(Movie movie) {
        return (double) movie.getRatingSum() / movie.getRatingCount();
    }

}

