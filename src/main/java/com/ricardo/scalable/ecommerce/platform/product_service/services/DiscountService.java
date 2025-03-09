package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Discount;

public interface DiscountService {

    Optional<Discount> findById(Long id);

    Optional<Discount> findByProductSkuId(Long productSkuId);

    Optional<Discount> findByDiscountType(String discountType);

    Optional<Discount> findByDiscountValue(Double discountValue);

    Optional<Discount> verifyValidityPeriod(Long discountId);

    int checkOverlappingDiscount(Long productSkuId, LocalDateTime newStartDate, LocalDateTime newEndDate);

    List<Discount> findAll();

    Optional<Discount> save(Discount discount);

    Optional<Discount> update(Discount discount);

    void delete(Long id);

}
