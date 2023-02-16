package com.cinema.cinema.themes.order.model;

import com.cinema.cinema.themes.couponCode.model.CouponCodeInputDto;
import com.cinema.cinema.themes.ticket.model.TicketIdDto;
import com.cinema.cinema.themes.user.model.StandardUserIdDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderInputDto {

    private StandardUserIdDto user;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Set<TicketIdDto> tickets = new HashSet<>();
    private CouponCodeInputDto couponCode;

}
