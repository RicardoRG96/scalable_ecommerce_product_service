package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;

public interface ProductAttributeRepository extends CrudRepository<ProductAttribute, Long> {

}
