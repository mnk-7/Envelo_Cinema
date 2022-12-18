package com.cinema.cinema.services.content;

import com.cinema.cinema.models.categories.AgeRestriction;

//@Service - is it needed? //TODO
public abstract class ContentService<T> {

    public abstract T getContent(long id);

    public abstract T addContent(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl);

    public abstract T editContent(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl);

}
