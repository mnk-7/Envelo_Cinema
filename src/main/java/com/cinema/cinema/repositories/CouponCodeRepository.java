package com.cinema.cinema.repositories;

import com.cinema.cinema.models.CouponCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CouponCodeRepository extends JpaRepository<CouponCode, Long> {

    Optional<CouponCode> findByCode(String code);

    List<CouponCode> findAllByOrderNull();

}
