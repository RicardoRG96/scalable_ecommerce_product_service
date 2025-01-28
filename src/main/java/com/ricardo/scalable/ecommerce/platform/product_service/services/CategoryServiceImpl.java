package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Category> findByCategoryId(Long categoryId) {
        return categoryRepository.findByCategoryId(categoryId);
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
        Category parentCategory = categoryRepository.findByCategoryId(categoryCreation.getParentId()).orElseThrow();
        category.setName(categoryCreation.getName());
        category.setDescription(categoryCreation.getDescription());
        category.setParent(parentCategory);
        
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
        Category unknownCategory = categoryRepository.findByName("Unknown").orElseThrow();
        List<Product> products = productRepository.findByCategoryId(id).orElseThrow();

        products.forEach(product -> {
            product.setCategory(unknownCategory);
            productRepository.save(product);
        });

        categoryRepository.deleteById(id);
    }

}
