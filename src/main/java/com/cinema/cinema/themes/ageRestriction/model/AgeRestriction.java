package com.cinema.cinema.themes.ageRestriction.model;

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
@Entity
@Table(name = "age_restrictions")
public class AgeRestriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(max = 20, message = "Field cannot contain more than {max} characters")
    private String minAge;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgeRestriction that = (AgeRestriction) o;
        return minAge.equals(that.minAge);
    }

    @Override
    public int hashCode() {
        return Objects.hash(minAge);
    }

}
