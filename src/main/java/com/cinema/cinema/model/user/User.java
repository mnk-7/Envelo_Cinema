package com.cinema.cinema.model.user;

import com.cinema.cinema.model.categories.Role;

public abstract class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;

}
