package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

public interface ProductSkuService {

    Optional<ProductSku> findById(Long id);

    Optional<List<ProductSku>> findByProductId(Long id);

    Optional<ProductSku> findBySku(String sku);

    Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive);

    Optional<List<ProductSku>> findBySizeAttributeId(Long sizeAttributeId);

    Optional<List<ProductSku>> findByColorAttributeId(Long colorAttributeId);

    Optional<List<ProductSku>> findByProductIdAndSizeAttributeId(Long productId, Long sizeAttributeId);

    Optional<List<ProductSku>> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId);

    Optional<ProductSku> findByProductIdAndSizeAttributeIdAndColorAttributeId(
        Long productId, Long sizeAttributeId, Long colorAttributeId);

    Iterable<ProductSku> findAll();

    Optional<ProductSku> save(ProductSkuCreationDto productSku);

    Optional<ProductSku> update(ProductSku productSku, Long id);

    void delete(Long id);

}
