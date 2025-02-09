package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;

public class ProductControllerTestData {

    public static Product createProduct001() {
        Product product = new Product();
        Category category = new Category(
            2L, 
            "Tecnologia", 
            "Descripcion tecnologia",
            null,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Category subCategory = new Category(
            6L, 
            "Smartphone", 
            "Telefonos celulares",
            category,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            2L, 
            "Apple", 
            "Marca líder en tecnologia", 
            "https://example.com/apple_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(1L);
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setCategory(subCategory);
        product.setBrand(brand);
        product.setCover("https://example.com/images/iphone15.jpg");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

    public static ProductCreationDto createProductCreationDto() {
        ProductCreationDto productCreationDto = new ProductCreationDto();
        productCreationDto.setName("Camiseta FC Barcelona");
        productCreationDto.setDescription("Descripcion camiseta FC Barcelona");
        productCreationDto.setCategoryId(8L);
        productCreationDto.setBrandId(4L);
        productCreationDto.setCover("https://example.com/camiseta_barcelona.png");

        return productCreationDto;
    }

}
