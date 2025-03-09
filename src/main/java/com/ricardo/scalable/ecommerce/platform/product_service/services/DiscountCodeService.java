package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

public interface DiscountCodeService {

    Optional<DiscountCode> findById(Long id);

    Optional<DiscountCode> findByCode(String code);

    Optional<List<DiscountCode>> findByDiscountId(Long discountId);

    Optional<DiscountCode> findByCodeAndDiscountId(String code, Long discountId);

    Optional<List<DiscountCode>> findByUsageLimit(Integer usageLimit);

    Optional<List<DiscountCode>> findByUsedCount(Integer usedCount);

    List<DiscountCode> findAll();

    Optional<DiscountCode> save(DiscountCodeDto discountCode);

    Optional<DiscountCode> update(DiscountCodeDto discountCode, Long id);

    void delete(Long id);

}
