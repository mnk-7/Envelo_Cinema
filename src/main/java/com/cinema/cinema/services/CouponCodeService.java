package com.cinema.cinema.services;

import com.cinema.cinema.repositories.CouponCodeRepository;
import com.cinema.cinema.exceptions.CouponCodeException;
import com.cinema.cinema.models.CouponCode;
import com.cinema.cinema.models.Order;
import com.cinema.cinema.utils.CouponCodeGenerator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CouponCodeService {

    private CouponCodeRepository couponCodeRepository;

    public CouponCode getCouponCode(String code) {
        Optional<CouponCode> couponCode = couponCodeRepository.findByCode(code);
        if (couponCode.isEmpty()) {
            throw new CouponCodeException("Coupon code with given ID not found");
        }
        return couponCode.get();
    }

    public List<CouponCode> getAllActiveCouponCodes() {
        return couponCodeRepository.findAllByOrderNull();
    }

    public void addCouponCode() {
        CouponCode couponCode = new CouponCode();
        couponCode.setCode(CouponCodeGenerator.generateCouponCode());
        couponCode.setOrder(null);
        couponCodeRepository.save(couponCode);
    }

    public void useCode(String code, Order order) {
        CouponCode couponCode = getCouponCode(code);
        Order couponCodeOrder = couponCode.getOrder();
        if (couponCodeOrder != null) {
            throw new CouponCodeException("Coupon code has already been used");
        }
        couponCode.setOrder(order);
        couponCodeRepository.save(couponCode);
    }

}
