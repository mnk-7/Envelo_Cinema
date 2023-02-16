package com.cinema.cinema.themes.couponCode.model;

import com.cinema.cinema.themes.order.model.Order;
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
@Entity
@Table(name = "coupon_codes")
public class CouponCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Field is mandatory")
    @NotBlank(message = "Field cannot be empty or blank")
    @Size(min = 10, max = 15, message = "Field must contain between {min} and {max} characters")
    private String code;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
