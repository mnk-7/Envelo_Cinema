package com.cinema.cinema.models;

import com.cinema.cinema.models.seat.Seat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venues")
public class Venue { //created for each new screening hall configuration

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int rowsNumber;
    private int columnsNumber;

    @OneToMany(mappedBy = "venue")
    private Set<Seat> seats;

    private boolean isActive;

}
