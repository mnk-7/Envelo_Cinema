package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestrictionIdDto;
import com.cinema.cinema.themes.genre.model.GenreIdDto;
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
public class MovieInputDto {

    private String title;
    private Integer durationInMinutes;
    private AgeRestrictionIdDto ageRestriction;
    private List<GenreIdDto> genres = new ArrayList<>();
    private boolean isPremiere;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
