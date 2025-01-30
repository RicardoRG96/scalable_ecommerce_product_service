package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

public interface ProductAttributeService {

    Optional<ProductAttribute> findById(Long id);

    Optional<ProductAttribute> findByType(String type);

    Optional<ProductAttribute> findByValue(String value);

    Iterable<ProductAttribute> findAll();

    Optional<ProductAttribute> save(ProductAttributeCreationDto productAttribute);

    Optional<ProductAttribute> update(ProductAttribute productAttribute, Long id);

    void delete(Long id);

}
