package com.cinema.cinema.themes.content.service;

import com.cinema.cinema.themes.ageRestriction.AgeRestrictionValidator;
import com.cinema.cinema.themes.content.model.Movie;
import com.cinema.cinema.themes.content.repository.MovieRepository;
import com.cinema.cinema.themes.content.validator.MovieValidator;
import com.cinema.cinema.themes.genre.GenreValidator;
import com.cinema.cinema.themes.genre.model.Genre;
import com.cinema.cinema.themes.user.model.StandardUser;
import com.cinema.cinema.themes.user.validator.StandardUserValidator;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class MovieService extends ContentService<Movie> {

    private final MovieRepository movieRepository;
    private final MovieValidator movieValidator;
    private final GenreValidator genreValidator;
    private final StandardUserValidator userValidator;
    private final AgeRestrictionValidator ageRestrictionValidator;

    @Override
    @Transactional(readOnly = true)
    public List<Movie> getAllContents() {
        List<Movie> movies = movieRepository.findAll(Sort.by("title"));
        return movies.stream()
                .peek(movie -> movie.setRating(calculateRating(movie)))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<Movie> getMoviesToWatch(long userId) {
        StandardUser user = userValidator.validateExists(userId);
        Set<Movie> moviesToWatch = user.getMoviesToWatch();
        return moviesToWatch.stream()
                .sorted(Comparator.comparing(Movie::getTitle))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Movie getContent(long movieId) {
        Movie movie = movieValidator.validateExists(movieId);
        movie.setRating(calculateRating(movie));
        return movie;
    }

    @Override
    @Transactional
    public Movie addContent(Movie movie) {
        movieValidator.validateInput(movie);
        ageRestrictionValidator.validateExists(movie.getAgeRestriction().getId());
        for (Genre genre : movie.getGenres()) {
            genreValidator.validateExists(genre.getId());
        }
        return movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void editContent(long movieId, Movie movieFromDto) {
        Movie movie = movieValidator.validateExists(movieId);
        movieValidator.validateInput(movieFromDto);
        ageRestrictionValidator.validateExists(movie.getAgeRestriction().getId());
        for (Genre genre : movie.getGenres()) {
            genreValidator.validateExists(genre.getId());
        }
        setFields(movie, movieFromDto);
        movieRepository.save(movie);
    }

    @Transactional
    public void rateMovie(Movie movie, int rate) {
        movieValidator.validateRate(rate);
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

