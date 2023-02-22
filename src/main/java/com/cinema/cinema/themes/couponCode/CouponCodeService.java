package com.cinema.cinema.themes.couponCode;

import com.cinema.cinema.themes.couponCode.model.CouponCode;
import com.cinema.cinema.themes.order.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CouponCodeService {

    private final CouponCodeRepository couponCodeRepository;
    private final CouponCodeValidator couponCodeValidator;

    @Transactional(readOnly = true)
    public CouponCode getCouponCode(long couponCodeId) {
        return couponCodeValidator.validateExists(couponCodeId);
    }

    @Transactional(readOnly = true)
    public List<CouponCode> getAllAvailableCouponCodes() {
        return couponCodeRepository.findAllByOrderNull();
    }

    @Transactional
    public CouponCode addCouponCode(CouponCode code) {
        CouponCode couponCode = new CouponCode();
        couponCode.setCode(CouponCodeGenerator.generateCouponCode());
        couponCode.setOrder(null);
        couponCode.setDiscountPercent(code.getDiscountPercent());
        couponCodeValidator.validateInput(couponCode);
        return couponCodeRepository.save(couponCode);
    }

    @Transactional
    public CouponCode useCouponCode(String code, Order order) {
        CouponCode couponCode = couponCodeValidator.validateExists(code);
        couponCodeValidator.validateNotUsed(couponCode);
        couponCode.setOrder(order);
        return couponCodeRepository.save(couponCode);
    }

    public void removeOrder(CouponCode couponCode) {
        couponCode.setOrder(null);
        couponCodeRepository.save(couponCode);
    }

}
