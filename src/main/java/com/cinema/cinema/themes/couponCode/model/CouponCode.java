package com.cinema.cinema.themes.couponCode.model;

import com.cinema.cinema.themes.order.model.Order;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coupon_codes")
public class CouponCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    private String code;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @NotNull(message = "Field is mandatory")
    @Min(value = 1, message = "Value cannot be less than {value}")
    @Max(value = 100, message = "Value cannot be more than {value}")
    private Integer discountPercent;

}
