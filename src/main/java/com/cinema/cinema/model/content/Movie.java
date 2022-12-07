package com.cinema.cinema.model.content;

import com.cinema.cinema.model.categories.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Content {

    private Set<Genre> genres;
    private Double rating;
    private Boolean isPremiere;

}
