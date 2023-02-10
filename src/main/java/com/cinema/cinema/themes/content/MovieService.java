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

    private final MovieRepository movieRepository;
    private final MovieValidator movieValidator;
    private final DtoMapperService mapperService;

    @Override
    @Transactional(readOnly = true)
    public List<MovieDtoRead> getAllContents() {
        List<Movie> movies = movieRepository.findAll(Sort.by("title"));
        return movies.stream()
                .peek(movie -> movie.setRating(calculateRating(movie)))
                .map(mapperService::mapToMovieDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MovieDtoRead getContent(long movieId) {
        Movie movie = getMovie(movieId);
        return mapperService.mapToMovieDto(movie);
    }

    @Transactional(readOnly = true)
    public Movie getMovie(long movieId) {
        Movie movie = movieValidator.validateExists(movieId);
        movie.setRating(calculateRating(movie));
        return movie;
    }

    @Override
    @Transactional
    public MovieDtoRead addContent(MovieDtoWrite movieDto) {
        Movie movie = mapperService.mapToMovie(movieDto);
        movieValidator.validateInput(movie);
        movie = movieRepository.save(movie);
        return mapperService.mapToMovieDto(movie);
    }

    @Override
    @Transactional
    public void editContent(long movieId, MovieDtoWrite movieDto) {
        Movie movie = movieValidator.validateExists(movieId);
        Movie movieFromDto = mapperService.mapToMovie(movieDto);
        movieValidator.validateInput(movieFromDto);
        setFields(movie, movieFromDto);
        movieRepository.save(movie);
    }

    @Transactional
    public void rateMovie(Movie movie, int rate) {
        movie.setRatingCount(movie.getRatingCount() + 1);
        movie.setRatingSum(movie.getRatingSum() + rate);
        movieRepository.save(movie);
    }

    private void setFields(Movie movie, Movie movieFromDto) {
        movie.setTitle(movieFromDto.getTitle());
        movie.setDurationInMinutes(movieFromDto.getDurationInMinutes());
        movie.setAgeRestriction(movieFromDto.getAgeRestriction());
        movie.setShortDescription(movieFromDto.getShortDescription());
        movie.setLongDescription(movieFromDto.getLongDescription());
        movie.setImageUrl(movieFromDto.getImageUrl());
        movie.setGenres(movieFromDto.getGenres());
        movie.setPremiere(movieFromDto.isPremiere());
    }

    private double calculateRating(Movie movie) {
        if (movie.getRatingCount() == 0) {
            return 0;
        }
        return (double) movie.getRatingSum() / movie.getRatingCount();
    }

}

