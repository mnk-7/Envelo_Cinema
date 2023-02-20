package com.cinema.cinema.themes.content.service;

import java.util.List;

public abstract class ContentService<T> {

    protected abstract T getContent(long id);

    protected abstract List<T> getAllContents();

    protected abstract T addContent(T t);

    protected abstract void editContent(long id, T t);

}
