package com.cinema.cinema.themes.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderShortDto {

    private Long id;
    private LocalDateTime createdAt;
    private BigDecimal price;

}
