package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.genre.model.GenreOutputDto;
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
    private Integer durationInMinutes;
    private AgeRestrictionOutputDto ageRestriction;
    private Set<GenreOutputDto> genres = new HashSet<>();
    private double rating;
    private boolean isPremiere;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
