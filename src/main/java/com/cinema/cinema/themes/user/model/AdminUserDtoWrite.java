package com.cinema.cinema.themes.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserDtoWrite {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Integer phone;

}
