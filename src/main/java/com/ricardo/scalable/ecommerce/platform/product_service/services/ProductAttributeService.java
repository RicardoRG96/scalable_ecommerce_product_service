package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

public interface ProductAttributeService {

    Optional<ProductAttribute> findById(Long id);

    Optional<List<ProductAttribute>> findByType(String type);

    Optional<ProductAttribute> findByValue(String value);

    Iterable<ProductAttribute> findAll();

    ProductAttribute save(ProductAttributeCreationDto productAttribute);

    Optional<ProductAttribute> update(ProductAttribute productAttribute, Long id);

    void delete(Long id);

}
