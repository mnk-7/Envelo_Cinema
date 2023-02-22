package com.cinema.cinema.themes.couponCode;

//TODO
public class CouponCodeGenerator {

    private static int counter = 0;

    public static String generateCouponCode() {
        counter++;
        return "CouponCode-" + counter;
    }

}
