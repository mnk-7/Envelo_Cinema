package com.cinema.cinema.model.content;

import com.cinema.cinema.model.categories.Genre;

import java.util.Set;

public class Movie extends Content {

    private Set<Genre> genres;
    private Double rating;
    private Boolean isPremiere;

}
