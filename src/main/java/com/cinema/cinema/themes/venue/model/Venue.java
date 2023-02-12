package com.cinema.cinema.themes.venue.model;

import com.cinema.cinema.themes.seat.model.Seat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venues")
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(max = 30, message = "Field cannot contain more than {max} characters")
    private String name;

    @NotNull(message = "Field is mandatory")
    @Min(value = 1, message = "Value cannot be less than {value}")
    private Integer rowsNumber;

    @NotNull(message = "Field is mandatory")
    @Min(value = 1, message = "Value cannot be less than {value}")
    private Integer columnsNumber;

    @OneToMany(mappedBy = "venue")
    @JsonManagedReference
    private Set<Seat> seats = new HashSet<>();

    private boolean isActive;

}
