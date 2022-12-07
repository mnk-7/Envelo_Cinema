package com.cinema.cinema.repository;

import com.cinema.cinema.model.content.Movie;

import java.util.ArrayList;
import java.util.List;

//TODO
public class MovieRepository {

    public Movie findMovieById(Long id){
        return new Movie();
    }

    public List<Movie> findAllMovies(){
        return new ArrayList<>();
    }

    public void create(Movie movie){}

    public void update(Long id, Movie movie){}

}
