package com.cinema.cinema.services;

import com.cinema.cinema.exceptions.MovieException;
import com.cinema.cinema.models.categories.AgeRestriction;
import com.cinema.cinema.models.categories.Genre;
import com.cinema.cinema.models.content.Movie;
import com.cinema.cinema.repositories.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public Movie getMovie(long id){
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
