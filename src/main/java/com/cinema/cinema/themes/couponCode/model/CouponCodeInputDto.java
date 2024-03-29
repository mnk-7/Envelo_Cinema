package com.cinema.cinema.themes.couponCode.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponCodeInputDto {

    private Integer discountPercent;
    private String code;

}
