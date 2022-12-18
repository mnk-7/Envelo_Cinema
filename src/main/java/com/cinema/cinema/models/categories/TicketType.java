package com.cinema.cinema.models.categories;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TicketType {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean isAvailable;

}
