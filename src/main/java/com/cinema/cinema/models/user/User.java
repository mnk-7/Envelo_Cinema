package com.cinema.cinema.models.user;

import com.cinema.cinema.models.categories.Role;

public abstract class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Role role;

}
