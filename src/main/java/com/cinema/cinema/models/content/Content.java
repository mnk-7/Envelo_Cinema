package com.cinema.cinema.models.content;

import com.cinema.cinema.models.categories.AgeRestriction;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity - where it should be located? //TODO
public abstract class Content {

    private Long id;
    private String title;
    private Integer duration; //in minutes
    private AgeRestriction ageRestriction;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}