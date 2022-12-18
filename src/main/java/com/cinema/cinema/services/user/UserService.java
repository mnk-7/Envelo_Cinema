package com.cinema.cinema.services.user;

//@Service - is it needed? //TODO
public abstract class UserService<T> {

    public abstract T getUser(long id);

    public abstract void editUser(long id, String firstName, String lastName, String password, String email, int phone);

}
