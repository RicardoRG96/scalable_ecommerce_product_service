package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    
    Optional<Category> findByCategoryId(Long categoryId);

    Optional<Category> findByName(String name);

}
