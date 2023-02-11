package com.cinema.cinema.themes.content.model;

import com.cinema.cinema.themes.ageRestriction.model.AgeRestriction;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(min = 3, max = 50, message = "Field must contain between {min} and {max} characters")
    private String title;

    @Min(value = 15, message = "Value cannot be less than {value}")
    @Column(name = "duration")
    private int durationInMinutes;

    @NotNull(message = "Field is mandatory")
    @ManyToOne
    @JoinColumn(name = "age_restr_id")
    private AgeRestriction ageRestriction;

    @NotNull(message = "Field is mandatory")
    @Size(min = 10, max = 500, message = "Field must contain between {min} and {max} characters")
    private String shortDescription;

    @Size(max = 2000, message = "Field cannot contain more than {max} characters")
    private String longDescription;

    @Size(max = 2048, message = "Field cannot contain more than {max} characters")
    private String imageUrl;

}
