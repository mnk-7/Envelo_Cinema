package com.cinema.cinema.themes.user.service;

public abstract class UserService<R, W> {

    protected abstract R getUser(long id);

    protected abstract void editUser(long id, W w);

}
