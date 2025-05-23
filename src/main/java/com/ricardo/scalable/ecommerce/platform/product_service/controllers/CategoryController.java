package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.CategoryService;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories/{id}")
    public ResponseEntity<Category> getById(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (categoryOptional.isPresent()) {
            return ResponseEntity.ok(categoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categories/name/{name}")
    public ResponseEntity<Category> getByName(@PathVariable String name) {
        Optional<Category> categoryOptional = categoryService.findByName(name);

        if (categoryOptional.isPresent()) {
            return ResponseEntity.ok(categoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<Iterable<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(
        @Valid @RequestBody CategoryCreationDto categoryCreation, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.save(categoryCreation));
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory(
        @Valid @RequestBody Category category, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Category> categoryOptional = categoryService.update(category, id);

        if (categoryOptional.isPresent()) {
            return ResponseEntity.ok(categoryOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Optional<Category> categoryOptional = categoryService.findById(id);

        if (categoryOptional.isPresent()) {
            categoryService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
