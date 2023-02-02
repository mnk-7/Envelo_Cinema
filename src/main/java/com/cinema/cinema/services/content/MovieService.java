package com.cinema.cinema.services.content;

import com.cinema.cinema.models.AgeRestriction;
import com.cinema.cinema.models.Genre;
import com.cinema.cinema.models.content.Movie;
import com.cinema.cinema.exceptions.MovieException;
import com.cinema.cinema.repositories.content.MovieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class MovieService extends ContentService<Movie> {

    private MovieRepository movieRepository;

    public Movie getMovie(long id) {
        return getContent(id);
    }

    //TODO - getContent should use getMovie
    @Override
    protected Movie getContent(long id) {
        Optional<Movie> movie = movieRepository.findById(id);
        if (movie.isEmpty()) {
            throw new MovieException("Movie wit given ID not found");
        }
        return movie.get();
    }

    public List<Movie> getAllMovies() {
        return getAllContents();
    }

    @Override
    protected List<Movie> getAllContents() {
        return movieRepository.findAll();
    }

    public void addMovie(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere) {
        Movie movie = addContent(title, duration, ageRestriction, shortDescription, longDescription, imageUrl);
        movie.setGenres(genres);
        movie.setPremiere(isPremiere);
        movie.setRating(0.0);
        //movie.setRatingCount(0);
        movieRepository.save(movie);
    }

    //TODO - builder, addContent should use addMovie
    @Override
    protected Movie addContent(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDurationInMinutes(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        return movie;
    }

    public void editMovie(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere) {
        Movie movie = editContent(id, title, duration, ageRestriction, shortDescription, longDescription, imageUrl);
        movie.setGenres(genres);
        movie.setPremiere(isPremiere);
        movieRepository.save(movie);
    }

    //TODO - whole object instead of many parameters
    @Override
    protected Movie editContent(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl) {
        Movie movie = getContent(id);
        movie.setTitle(title);
        movie.setDurationInMinutes(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        return movie;
    }

    public void rateMovie(Movie movie, int rate) {
        //movie.setRatingCount(movie.getRatingCount() + 1);
        //double newRating = (movie.getRating() + rate) / movie.getRatingCount();
        //movie.setRating(newRating);
        movieRepository.save(movie);
    }
}

