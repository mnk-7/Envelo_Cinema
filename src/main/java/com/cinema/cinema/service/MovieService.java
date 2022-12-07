package com.cinema.cinema.service;

import com.cinema.cinema.model.categories.AgeRestriction;
import com.cinema.cinema.model.categories.Genre;
import com.cinema.cinema.model.content.Movie;
import com.cinema.cinema.repository.MovieRepository;

import java.util.List;
import java.util.Set;

public class MovieService {

    private MovieRepository movieRepository;

    public Movie getMovie(Long id){
        return movieRepository.findMovieById(id);
    }

    public List<Movie> getAllMovies(){
        return movieRepository.findAllMovies();
    }

    public void addMovie(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl, Set<Genre> genre, boolean isPremiere){
        Movie movie = new Movie();

        movieRepository.create(movie);
    }

}
