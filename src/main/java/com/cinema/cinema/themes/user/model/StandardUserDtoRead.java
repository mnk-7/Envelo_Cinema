package com.cinema.cinema.themes.user.model;

import com.cinema.cinema.themes.cart.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardUserDtoRead {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Integer phone;
    private boolean isActive;
    private Cart cart;

}
