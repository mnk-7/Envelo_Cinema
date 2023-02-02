package com.cinema.cinema.models.content;

import com.cinema.cinema.models.AgeRestriction;

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

    private String title;
    private int durationInMinutes;

    @ManyToOne
    @JoinColumn(name = "age_restr_id")
    private AgeRestriction ageRestriction;

    private String shortDescription;
    private String longDescription;
    private String imageUrl;

}
