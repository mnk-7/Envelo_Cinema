package com.cinema.cinema.models.user;

import com.cinema.cinema.models.categories.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@Entity - where it should be located? //TODO
public abstract class User {

    private Long id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Integer phone;
    private Role role;

}
