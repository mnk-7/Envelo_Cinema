package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;
import com.cinema.cinema.themes.genre.model.Genre;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieDtoWrite {

    private String title;
    private int durationInMinutes;
    private AgeRestriction ageRestriction;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;
    private Set<Genre> genres;
    private boolean isPremiere;

}
