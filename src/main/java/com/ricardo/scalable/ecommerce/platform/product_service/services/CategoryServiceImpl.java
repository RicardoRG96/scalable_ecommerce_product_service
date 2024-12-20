package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Iterable<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(CategoryCreationDto categoryCreation) {
        Category category = new Category();
        category.setName(categoryCreation.getName());
        category.setDescription(categoryCreation.getDescription());
        
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> update(Category category, Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            Category dbCategory = categoryOptional.orElseThrow();
            dbCategory.setName(category.getName());
            dbCategory.setDescription(category.getDescription());

            return Optional.of(categoryRepository.save(dbCategory));
        }

        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

}
