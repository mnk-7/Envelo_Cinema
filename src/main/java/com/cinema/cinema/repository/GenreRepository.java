package com.cinema.cinema.repository;

import com.cinema.cinema.model.categories.Genre;
import com.cinema.cinema.model.content.Movie;

import java.util.ArrayList;
import java.util.List;

//TODO
public class GenreRepository {

    public Genre findGenreById(){
        return new Genre();
    }

    public List<Genre> findAllGenres(){
        return new ArrayList<>();
    }

    public List<Genre> findAllGenresByMovie(Movie movie){
        return new ArrayList<>();
    }

    public void create(Genre genre){}

    public void update(long id, Genre genre){}

}
