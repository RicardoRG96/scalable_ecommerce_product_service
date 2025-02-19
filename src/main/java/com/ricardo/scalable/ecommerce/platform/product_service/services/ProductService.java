package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;

public interface ProductService {

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Iterable<Product> findAll();

    Optional<Product> save(ProductCreationDto product);

    Optional<Product> update(Product product, Long id);

    void delete(Long id);

}
