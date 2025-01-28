package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

public interface ProductSkuService {

    Optional<ProductSku> findByProductId(Long id);

    Optional<ProductSku> findBySku(String sku);

    Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive);

    Optional<ProductSku> save(ProductSkuCreationDto productSku);

    Optional<ProductSku> update(ProductSku productSku, Long id);

    void delete(Long id);

}
