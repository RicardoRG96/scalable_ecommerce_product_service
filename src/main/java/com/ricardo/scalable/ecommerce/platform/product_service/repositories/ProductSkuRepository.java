package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;

public interface ProductSkuRepository extends CrudRepository<ProductSku, Long> {

    Optional<List<ProductSku>> findByProductId(Long id);

    Optional<ProductSku> findBySku(String sku);

    Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive);

    Optional<List<ProductSku>> findBySizeAttributeId(Long sizeAttributeId);

    Optional<List<ProductSku>> findByColorAttributeId(Long colorAttributeId);

    Optional<List<ProductSku>> findByProductIdAndSizeAttributeId(Long productId, Long sizeAttributeId);

    Optional<List<ProductSku>> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId);

    Optional<ProductSku> 
        findByProductIdAndSizeAttributeIdAndColorAttributeId(
            Long productId, Long sizeAttributeId, Long colorAttributeId);

}
