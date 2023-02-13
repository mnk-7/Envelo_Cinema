package com.cinema.cinema.themes.content.model;

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
public class MovieInputDto {

    private String title;
    private Integer durationInMinutes;
    private Long ageRestrictionId;
    private Set<Long> genresId = new HashSet<>();
    private boolean isPremiere;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
