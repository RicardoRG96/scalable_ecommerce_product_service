package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public interface ProductService {

    Optional<Product> findById(Long id);

    Optional<Product> findByName(String name);

    Optional<Product> findBySku(String sku);

    Iterable<Product> findAll();

    Product save(Product product);

    Optional<Product> update(Product product, Long id);

    void delete(Long id);

}
