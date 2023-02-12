package com.cinema.cinema.themes.ageRestriction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgeRestrictionOutputDto {

    private Long id;
    private String minAge;

}
