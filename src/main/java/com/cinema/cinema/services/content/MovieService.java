package com.cinema.cinema.services.content;

import com.cinema.cinema.exceptions.MovieException;
import com.cinema.cinema.models.categories.AgeRestriction;
import com.cinema.cinema.models.categories.Genre;
import com.cinema.cinema.models.content.Movie;
import com.cinema.cinema.repositories.content.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService extends ContentService<Movie> {

    private MovieRepository movieRepository;

    public Movie getMovie(long id){
        return getContent(id);
    }

    @Override
    public Movie getContent(long id){
        Optional<Movie> movie = movieRepository.findMovieById(id);
        if (movie.isEmpty()){
            throw new MovieException("Movie wit given ID not found");
        }
        return movie.get();
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAllMovies();
    }

    public void addMovie(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere){
        Movie movie = addContent(title, duration, ageRestriction, shortDescription, longDescription, imageUrl);
        movie.setGenres(genres);
        movie.setIsPremiere(isPremiere);
        movie.setRating(0.0);
        movieRepository.create(movie);
    }

    @Override
    public Movie addContent(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl){
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        return movie;
    }

    public void editMovie(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere){
        Movie movie = editContent(id, title, duration, ageRestriction, shortDescription, longDescription, imageUrl);
        movie.setGenres(genres);
        movie.setIsPremiere(isPremiere);
        movieRepository.update(id, movie);
    }

    @Override
    public Movie editContent(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl){
        Movie movie = getContent(id);
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        return movie;
    }

    public void editIsPremiere(long id, boolean isPremiere){
        Movie movie = getContent(id);
        movie.setIsPremiere(isPremiere);
        movieRepository.update(id, movie);
    }

}
