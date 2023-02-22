package com.cinema.cinema.themes.couponCode;

import com.cinema.cinema.exceptions.ArgumentNotValidException;
import com.cinema.cinema.exceptions.ElementNotFoundException;
import com.cinema.cinema.themes.couponCode.model.CouponCode;
import com.cinema.cinema.themes.order.model.Order;
import com.cinema.cinema.utils.ValidatorService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponCodeValidator extends ValidatorService<CouponCode> {

    private final CouponCodeRepository couponCodeRepository;

    public CouponCodeValidator(Validator validator, CouponCodeRepository couponCodeRepository) {
        super(validator);
        this.couponCodeRepository = couponCodeRepository;
    }

    @Override
    public CouponCode validateExists(long couponCodeId) {
        Optional<CouponCode> couponCode = couponCodeRepository.findById(couponCodeId);
        if (couponCode.isEmpty()) {
            throw new ElementNotFoundException("Coupon code with ID " + couponCodeId + " not found");
        }
        return couponCode.get();
    }

    public CouponCode validateExists(String code) {
        Optional<CouponCode> couponCode = couponCodeRepository.findByCode(code);
        if (couponCode.isEmpty()) {
            throw new ElementNotFoundException("Coupon code " + code + " not found");
        }
        return couponCode.get();
    }

    public void validateNotUsed(CouponCode couponCode) {
        Order couponCodeOrder = couponCode.getOrder();
        if (couponCodeOrder != null) {
            throw new ArgumentNotValidException("Coupon code has already been used");
        }
    }

}
