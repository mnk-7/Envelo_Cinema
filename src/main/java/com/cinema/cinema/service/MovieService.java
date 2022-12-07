package com.cinema.cinema.service;

import com.cinema.cinema.model.categories.AgeRestriction;
import com.cinema.cinema.model.categories.Genre;
import com.cinema.cinema.model.content.Movie;
import com.cinema.cinema.repository.MovieRepository;

import java.util.List;
import java.util.Set;

public class MovieService {

    private MovieRepository movieRepository;

    public Movie getMovie(long id){
        return movieRepository.findMovieById(id);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAllMovies();
    }

    public void addMovie(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere){
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        movie.setGenres(genres);
        movie.setIsPremiere(isPremiere);
        movie.setRating(0.0);
        movieRepository.create(movie);
    }

    public void editMovie(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genres, boolean isPremiere){
        Movie movie = getMovie(id);
        movie.setTitle(title);
        movie.setDuration(duration);
        movie.setAgeRestriction(ageRestriction);
        movie.setShortDescription(shortDescription);
        movie.setLongDescription(longDescription);
        movie.setImageUrl(imageUrl);
        movie.setGenres(genres);
        movie.setIsPremiere(isPremiere);
        movieRepository.update(id, movie);
    }

    public void editIsPremiere(long id, boolean isPremiere){
        Movie movie = getMovie(id);
        movie.setIsPremiere(isPremiere);
        movieRepository.update(id, movie);
    }

}
