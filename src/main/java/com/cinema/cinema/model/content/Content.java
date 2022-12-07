package com.cinema.cinema.model.content;

import com.cinema.cinema.model.categories.AgeRestriction;

public abstract class Content {

    private Long id;
    private String title;
    private Integer duration; //in minutes
    private AgeRestriction ageRestriction;
    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
