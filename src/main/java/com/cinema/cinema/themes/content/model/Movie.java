package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.genre.model.Genre;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie extends Content {

    @ManyToMany
    @JoinTable(name = "genres_in_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;

    @Transient
    private double rating;

    //private int ratingCount;
    private boolean isPremiere;

}
