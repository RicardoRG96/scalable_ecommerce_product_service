package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

public class CategoryServiceTestData {

    public static Iterable<Category> createListOfCategories() {
        Category parentCategory1 = createParentCategory001().orElseThrow();
        Category parentCategory2 = createParentCategory002().orElseThrow();
        Category parentCategory3 = createParentCategory003().orElseThrow();
        Category subCategory1 = createSubCategory001().orElseThrow();
        Category subCategory2 = createSubCategory002().orElseThrow();
        Category subCategory3 = createSubCategory003().orElseThrow();
        Category subCategory4 = createSubCategory004().orElseThrow();
        Category unknownCategory = createUnknownCategory().orElseThrow();

        return List.of(
            parentCategory1, 
            parentCategory2, 
            parentCategory3, 
            subCategory1, 
            subCategory2,
            subCategory3,
            subCategory4,
            unknownCategory
        );
    }

    public static Optional<Category> createParentCategory001() {
        Category category = new Category(
            1L, 
            "Tecnología", 
            "Descripcion tecnología",
            null,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createParentCategory002() {
        Category category = new Category(
            2L, 
            "Deportes", 
            "Descripcion deportes",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createParentCategory003() {
        Category category = new Category(
            3L, 
            "Ropa Hombre", 
            "Descripcion ropa hombre",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory001() {
        Category category = new Category(
            4L, 
            "Computadoras", 
            "Descripcion computadoras",
            createParentCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory002() {
        Category category = new Category(
            5L, 
            "Celulares", 
            "Descripcion celulares",
            createParentCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory003() {
        Category category = new Category(
            6L, 
            "Poleras", 
            "Descripcion poleras",
            createParentCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory004() {
        Category category = new Category(
            7L, 
            "Pantalones", 
            "Descripcion pantalones",
            createParentCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createUnknownCategory() {
        Category category = new Category(
            8L, 
            "Unknown", 
            "Unknown",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static CategoryCreationDto createCategoryCreationDto() {
        CategoryCreationDto categoryCreationDto = new CategoryCreationDto();
        categoryCreationDto.setName("Electrohogar");
        categoryCreationDto.setDescription("Descripcion electrohogar");
        categoryCreationDto.setParentId(null);

        return categoryCreationDto;
    }

    public static Category createCategoryCreationResponse() {
        return new Category(
            9L, 
            "Electrohogar", 
            "Descripcion electrohogar", 
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
    }

}
