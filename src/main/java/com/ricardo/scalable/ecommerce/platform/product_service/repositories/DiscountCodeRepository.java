package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;

public interface DiscountCodeRepository extends CrudRepository<DiscountCode, Long> {

    Optional<DiscountCode> findByCode(String code);

    Optional<List<DiscountCode>> findByDiscountId(Long discountId);

    Optional<DiscountCode> findByCodeAndDiscountId(String code, Long discountId);

    Optional<List<DiscountCode>> findByUsageLimit(Integer usageLimit);

    Optional<List<DiscountCode>> findByUsedCount(Integer usedCount);

}
