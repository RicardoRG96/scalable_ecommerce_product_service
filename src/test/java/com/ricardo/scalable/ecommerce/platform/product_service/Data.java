package com.ricardo.scalable.ecommerce.platform.product_service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public class Data {

    public static List<Product> createListOfProducts() {
        Product product1 = createProduct001().orElseThrow();
        Product product2 = createProduct002().orElseThrow();

        return List.of(product1, product2);
    }

    public static Optional<Product> createProduct001() {
        Product product = new Product();
        Category category = createCategory001().orElseThrow();
        Brand brand = createBrand002().orElseThrow();

        product.setId(1L);
        product.setSku("123456");
        product.setUpc("abcde");
        product.setName("Notebook ASUS");
        product.setDescription("Computador de ultima generacion ASUS");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(1000.99);
        product.setStock(100);
        product.setImageUrl("image.png");
        product.setIsActive(true);
        product.setIsFeatured(true);
        product.setIsOnSale(false);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct002() {
        Product product = new Product();
        Category category = createCategory002().orElseThrow();
        Brand brand = createBrand002().orElseThrow();

        product.setId(2L);
        product.setSku("7891011");
        product.setUpc("fghijk");
        product.setName("Macbook Apple");
        product.setDescription("Computador de ultima generacion Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(1300.99);
        product.setStock(80);
        product.setImageUrl("image2.png");
        product.setIsActive(true);
        product.setIsFeatured(true);
        product.setIsOnSale(false);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static List<Category> createListOfCategories() {
        Category category1 = createCategory001().orElseThrow();
        Category category2 = createCategory002().orElseThrow();

        return List.of(category1, category2);
    }

    public static Optional<Category> createCategory001() {
        Category category = new Category(
            2L, 
            "Tecnología", 
            "Descripcion tecnología 2", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createCategory002() {
        Category category = new Category(
            3L, 
            "Deportes", 
            "Descripcion deportes", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static List<Brand> createListOfBrands() {
        Brand brand1 = createBrand001().orElseThrow();
        Brand brand2 = createBrand002().orElseThrow();

        return List.of(brand1, brand2);
    }

    public static Optional<Brand> createBrand001() {
        Brand brand = new Brand(
            2L, 
            "Apple", 
            "Marca Apple", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand002() {
        Brand brand = new Brand(
            3L, 
            "Samsung", 
            "Marca Samsung", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Product productCreated() {
        return createProduct001().orElseThrow();
    }

}
