package com.cinema.cinema.themes.couponCode;

import com.cinema.cinema.themes.order.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CouponCodeService {

    private final CouponCodeRepository couponCodeRepository;

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
