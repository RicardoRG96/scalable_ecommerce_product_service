package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

public interface ProductSkuService {

    Optional<ProductSku> findById(Long id);

    Optional<Iterable<ProductSku>> findByProductId(Long id);

    Optional<ProductSku> findBySku(String sku);

    Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive);

    Optional<Iterable<ProductSku>> findByProductIdAndSizeAttributeId(Long productId, Long sizeAttributeId);

    Optional<Iterable<ProductSku>> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId);

    Optional<ProductSku> findByProductIdAndSizeAttributeIdAndColorAttributeId(
        Long productId, Long sizeAttributeId, Long colorAttributeId);

    Iterable<ProductSku> findAll();

    Optional<ProductSku> save(ProductSkuCreationDto productSku);

    Optional<ProductSku> update(ProductSku productSku, Long id);

    void delete(Long id);

}
