package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Discount;

public interface DiscountRepository extends CrudRepository<Discount, Long> {

    @Query(value = "SELECT d.id, d.discount_type, d.discount_value, d.start_date, d.end_date, d.is_active, d.created_at, d.updated_at " +
                   "FROM discounts d " +
                   "JOIN discount_product_sku dps ON d.id = dps.discount_id " +
                   "WHERE dps.product_sku_id = :productSkuId ",
                   nativeQuery = true)
    Optional<List<Discount>> findByProductSkuId(@Param("productSkuId") Long productSkuId);

    Optional<List<Discount>> findByDiscountType(String discountType);

    Optional<List<Discount>> findByDiscountValue(Double discountValue);

    @Query(
        "SELECT d FROM Discount d WHERE d.startDate <= CURRENT_TIMESTAMP AND d.endDate >= CURRENT_TIMESTAMP AND d.isActive = true AND d.id = ?1")
    Optional<Discount> verifyValidityPeriod(Long discountId);

    @Query(value = "SELECT COUNT(*) " +
                   "FROM discounts d " +
                   "JOIN discount_product_sku dps ON d.id = dps.discount_id " +
                   "WHERE dps.product_sku_id = :productSkuId " +
                   "AND d.is_active = TRUE " +
                   "AND (d.start_date <= :endDate AND d.end_date >= :startDate)",
                   nativeQuery = true)
    int checkOverlappingDiscount(@Param("productSkuId") Long productSkuId,
                                      @Param("startDate") LocalDateTime startDate,
                                      @Param("endDate") LocalDateTime endDate);

}
