package com.cinema.cinema.themes.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
