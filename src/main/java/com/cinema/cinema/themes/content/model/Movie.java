package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.genre.model.Genre;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private List<Genre> genres = new ArrayList<>();

    @Transient
    private double rating;

    @Min(value = 0, message = "Value cannot be less than {value}")
    private int ratingCount;

    @Min(value = 0, message = "Value cannot be less than {value}")
    private int ratingSum;

    private boolean isPremiere;

}
