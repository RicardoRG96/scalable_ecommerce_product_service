package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

public interface CategoryService {

    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    Iterable<Category> findAll();

    Category save(CategoryCreationDto category);

    Optional<Category> update(Category category, Long id);

    void delete(Long id);

}
