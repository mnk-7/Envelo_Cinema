package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionOutputDto;
import com.cinema.cinema.themes.genre.model.GenreOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieOutputDto {

    private Long id;
    private String title;
    private Integer durationInMinutes;
    private AgeRestrictionOutputDto ageRestriction;
    private List<GenreOutputDto> genres = new ArrayList<>();
    private double rating;
    private boolean isPremiere;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
