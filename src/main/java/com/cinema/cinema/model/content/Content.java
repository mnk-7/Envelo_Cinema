package com.cinema.cinema.model.content;

import com.cinema.cinema.model.categories.AgeRestriction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Content {

    private Long id;
    private String title;
    private Integer duration; //in minutes
    private AgeRestriction ageRestriction;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
