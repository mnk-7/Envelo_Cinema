package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.genre.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieOutputDto {

    private Long id;
    private String title;
    private int durationInMinutes;
    private AgeRestriction ageRestriction;
    private Set<Genre> genres = new HashSet<>();
    private double rating;
    private boolean isPremiere;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
