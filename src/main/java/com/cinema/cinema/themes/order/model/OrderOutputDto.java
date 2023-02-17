package com.cinema.cinema.themes.order.model;

import com.cinema.cinema.themes.couponCode.model.CouponCodeOutputDto;
import com.cinema.cinema.themes.ticket.model.TicketOutputDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderOutputDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Set<TicketOutputDto> tickets = new HashSet<>();
    private CouponCodeOutputDto couponCode;
    private String qrCode;
    private BigDecimal price;

}
