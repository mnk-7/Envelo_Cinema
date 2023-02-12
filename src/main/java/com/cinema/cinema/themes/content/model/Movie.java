package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.genre.model.Genre;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movies")
public class Movie extends Content {

    @NotNull(message = "Field is mandatory")
    @NotEmpty(message = "List cannot be empty")
    @ManyToMany
    @JoinTable(name = "genres_in_movies",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @Transient
    private double rating;

    @Min(value = 0, message = "Value cannot be less than {value}")
    private int ratingCount;

    @Min(value = 0, message = "Value cannot be less than {value}")
    private int ratingSum;

    private boolean isPremiere;

}
