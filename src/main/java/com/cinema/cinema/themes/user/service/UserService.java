package com.cinema.cinema.themes.user.service;

//@Service - is it needed? //TODO
public abstract class UserService<T> {

    protected abstract T getUser(long id);

    protected abstract T editUser(long id, String firstName, String lastName, String password, String email, int phone);

}