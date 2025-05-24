package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.DiscountType;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

public interface DiscountService {

    Optional<Discount> findById(Long id);

    Optional<List<Discount>> findByProductSkuId(Long productSkuId);

    Optional<List<Discount>> findByDiscountType(DiscountType discountType);

    Optional<List<Discount>> findByDiscountValue(Double discountValue);

    Optional<Discount> verifyValidityPeriod(Long discountId);

    int checkOverlappingDiscount(Long productSkuId, LocalDateTime newStartDate, LocalDateTime newEndDate);

    List<Discount> findAll();

    Optional<Discount> save(DiscountDto discount);

    Optional<Discount> update(DiscountDto discount, Long id);

    void delete(Long id);

}
