package com.cinema.cinema.themes.content.service;

import java.util.List;

public abstract class ContentService<R, W> {

    protected abstract R getContent(long id);

    protected abstract List<R> getAllContents();

    protected abstract R addContent(W w);

    protected abstract void editContent(long id, W w);

}
