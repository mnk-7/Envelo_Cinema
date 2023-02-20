package com.cinema.cinema.themes.user.service;

public abstract class UserService<T> {

    protected abstract T getUser(long id);

    protected abstract void editUser(long id, T t);

}
