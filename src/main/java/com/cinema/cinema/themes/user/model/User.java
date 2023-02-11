package com.cinema.cinema.themes.user.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(min = 2, max = 50, message = "Field must contain between {min} and {max} characters")
    private String firstName;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(min = 2, max = 50, message = "Field must contain between {min} and {max} characters")
    private String lastName;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(min = 5, max = 30, message = "Field must contain between {min} and {max} characters")
    private String password;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(max = 100, message = "Field cannot contain more than {max} characters")
    @Email(message = "Wrong email format")
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, phone, role);
    }

}
