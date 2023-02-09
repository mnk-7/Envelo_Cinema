package com.cinema.cinema.themes.ticketType.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ticket_types")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be blank or empty")
    @Size(max = 20, message = "Field cannot contain more than {max} characters")
    private String name;

    @NotNull(message = "Field is mandatory")
    @Size(max = 250, message = "Field cannot contain more than {max} characters")
    private String description;

    @NotNull(message = "Field is mandatory")
    @Min(value = 0, message = "Value cannot be less than {value}")
    private BigDecimal price;
    private boolean isAvailable;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketType that = (TicketType) o;
        return isAvailable == that.isAvailable && Objects.equals(name, that.name) && price.compareTo(that.price) == 0 && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, isAvailable);
    }

}
