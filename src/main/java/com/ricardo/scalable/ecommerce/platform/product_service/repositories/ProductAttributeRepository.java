package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;

public interface ProductAttributeRepository extends CrudRepository<ProductAttribute, Long> {

    Optional<ProductAttribute> findByType(String type);

    Optional<ProductAttribute> findByValue(String value);

}
