package com.ricardo.scalable.ecommerce.platform.product_service.integrationTestData;

import java.sql.Timestamp;
import java.time.Instant;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public class Data {

    public static Product createProduct001() {
        Product product = new Product();
        Category category = new Category(
            1L, 
            "Smartphone", 
            "Telefonos celulares", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            1L, 
            "Apple", 
            "Marca líder en tecnologia", 
            "https://example.com/apple_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(1L);
        product.setSku("SKU2210");
        product.setUpc("UPC6569");
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(1500.99);
        product.setStock(540);
        product.setImageUrl("https://example.com/images/iphone15_algodon.jpg");
        product.setIsActive(true);
        product.setIsFeatured(true);
        product.setIsOnSale(true);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

    public static Product createProduct002() {
        Product product = new Product();
        Category category = new Category(
            2L, 
            "Notebooks", 
            "Computadores portatiles", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            1L, 
            "ASUS", 
            "Empresa multinacional de tecnología", 
            "https://example.com/asus_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(2L);
        product.setSku("SKU2501");
        product.setUpc("UPC1515");
        product.setName("Asus Zenbook");
        product.setDescription("Notebook de ultima generacion");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(999.99);
        product.setStock(200);
        product.setImageUrl("https://example.com/images/asus_zenbook.jpg");
        product.setIsActive(true);
        product.setIsFeatured(false);
        product.setIsOnSale(true);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

    public static Product createProduct003() {
        Product product = new Product();
        Category category = new Category(
            4L, 
            "Deportes", 
            "Equipamiento deportivo, ropa deportiva, accesorios", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            3L, 
            "Nike", 
            "Marca deportiva estadounidense", 
            "https://example.com/nike_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(3L);
        product.setSku("SKU1010");
        product.setUpc("UPC7436");
        product.setName("Camiseta Liverpool");
        product.setDescription("Camiseta de futbol");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(100.99);
        product.setStock(158);
        product.setImageUrl("https://example.com/images/camiseta_liverpool.jpg");
        product.setIsActive(true);
        product.setIsFeatured(true);
        product.setIsOnSale(false);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

}
