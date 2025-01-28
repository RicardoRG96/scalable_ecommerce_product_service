package com.ricardo.scalable.ecommerce.platform.product_service.unitTestData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public class Data {

    // public static List<Product> createListOfProducts() {
    //     Product product1 = createProduct001().orElseThrow();
    //     Product product2 = createProduct002().orElseThrow();

    //     return List.of(product1, product2);
    // }

    // public static Optional<Product> createProduct001() {
    //     Product product = new Product();
    //     Category category = createSubCategory001().orElseThrow();
    //     Brand brand = createBrand002().orElseThrow();

    //     product.setId(1L);
    //     product.setSku("123456");
    //     product.setUpc("abcde");
    //     product.setName("Notebook ASUS");
    //     product.setDescription("Computador de ultima generacion ASUS");
    //     product.setCategory(category);
    //     product.setBrand(brand);
    //     product.setPrice(1000.99);
    //     product.setStock(100);
    //     product.setImageUrl("image.png");
    //     product.setIsActive(true);
    //     product.setIsFeatured(true);
    //     product.setIsOnSale(false);
    //     product.setCreatedAt(Timestamp.from(Instant.now()));
    //     product.setUpdatedAt(Timestamp.from(Instant.now()));

    //     return Optional.of(product);
    // }

    // public static Optional<Product> createProduct002() {
    //     Product product = new Product();
    //     Category category = createSubCategory002().orElseThrow();
    //     Brand brand = createBrand002().orElseThrow();

    //     product.setId(2L);
    //     product.setSku("7891011");
    //     product.setUpc("fghijk");
    //     product.setName("Macbook Apple");
    //     product.setDescription("Computador de ultima generacion Apple");
    //     product.setCategory(category);
    //     product.setBrand(brand);
    //     product.setPrice(1300.99);
    //     product.setStock(80);
    //     product.setImageUrl("image2.png");
    //     product.setIsActive(true);
    //     product.setIsFeatured(true);
    //     product.setIsOnSale(false);
    //     product.setCreatedAt(Timestamp.from(Instant.now()));
    //     product.setUpdatedAt(Timestamp.from(Instant.now()));

    //     return Optional.of(product);
    // }

    // public static Optional<Product> createProduct003() {
    //     Product product = new Product();
    //     Category category = createSubCategory001().orElseThrow();
    //     Brand brand = createBrand001().orElseThrow();

    //     product.setId(3L);
    //     product.setSku("789ads4248");
    //     product.setUpc("fs748542a");
    //     product.setName("Notebook Lenovo");
    //     product.setDescription("Computador de ultima generacion Lenovo");
    //     product.setCategory(category);
    //     product.setBrand(brand);
    //     product.setPrice(1500.99);
    //     product.setStock(850);
    //     product.setImageUrl("image3.png");
    //     product.setIsActive(true);
    //     product.setIsFeatured(true);
    //     product.setIsOnSale(false);
    //     product.setCreatedAt(Timestamp.from(Instant.now()));
    //     product.setUpdatedAt(Timestamp.from(Instant.now()));

    //     return Optional.of(product);
    // }

    public static List<Category> createListOfCategories() {
        Category category1 = createCategory001().orElseThrow();
        Category category2 = createCategory002().orElseThrow();
        Category subCategory1 = createSubCategory001().orElseThrow();
        Category subCategory2 = createSubCategory002().orElseThrow();

        return List.of(category1, category2, subCategory1, subCategory2);
    }

    public static Optional<Category> createCategory001() {
        Category category = new Category(
            2L, 
            "Tecnología", 
            "Descripcion tecnología 2",
            null,
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
            createCategory001().orElseThrow(), 
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
            createCategory001().orElseThrow(), 
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

    // public static Product productCreated() {
    //     return createProduct001().orElseThrow();
    // }

    // public static List<Map<String, Object>> createListOfProductsMap() {
    //     Product product1 = createProduct001().orElseThrow();
    //     Product product2 = createProduct002().orElseThrow();

    //     return List.of(
    //         Map.of(
    //             "objectID", product1.getId(),
    //             "name", product1.getName(),
    //             "description", product1.getDescription(),
    //             "price", product1.getPrice(),
    //             "sku", product1.getSku(),
    //             "upc", product1.getUpc(),
    //             "brand", product1.getBrand().getName(),
    //             "categories", product1.getCategory().getName()
    //         ),
    //         Map.of(
    //             "objectID", product2.getId(),
    //             "name", product2.getName(),
    //             "description", product2.getDescription(),
    //             "price", product2.getPrice(),
    //             "sku", product2.getSku(),
    //             "upc", product2.getUpc(),
    //             "brand", product2.getBrand().getName(),
    //             "categories", product2.getCategory().getName()
    //         )
    //     );
    // }

    // public static List<Product> createListOfProductsForSearch() {
    //     Product product1 = createProduct001().orElseThrow();
    //     Product product3 = createProduct003().orElseThrow();

    //     return List.of(product1, product3);
    // }

    // public static List<Product> createListOfFilterByCategory() {
    //     Product product2 = createProduct002().orElseThrow();
    //     Product product3 = createProduct003().orElseThrow();

    //     return List.of(product2, product3);
    // }

    // public static List<Product> createListOfFilterByBrand() {
    //     Product product1 = createProduct001().orElseThrow();
    //     Product product2 = createProduct002().orElseThrow();

    //     return List.of(product1, product2);
    // }

    // public static List<Product> createListOfFilterByPriceRange() {
    //     Product product1 = createProduct001().orElseThrow();
    //     Product product2 = createProduct002().orElseThrow();

    //     return List.of(product1, product2);
    // }

}
