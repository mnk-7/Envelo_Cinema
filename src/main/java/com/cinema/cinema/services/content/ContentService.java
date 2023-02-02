package com.cinema.cinema.services.content;

import com.cinema.cinema.models.AgeRestriction;

import java.util.List;

//@Service - is it needed? //TODO
public abstract class ContentService<T> {

    protected abstract T getContent(long id);

    protected abstract List<T> getAllContents();

    protected abstract T addContent(String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl);

    protected abstract T editContent(long id, String title, int duration, AgeRestriction ageRestriction, String shortDescription, String longDescription, String imageUrl);

}
