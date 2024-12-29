package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByName(String name);

    Optional<Product> findBySku(String sku);

    Optional<List<Product>> findByCategoryId(Long categoryId);

    Optional<List<Product>> findByBrandId(Long brandId);

}
