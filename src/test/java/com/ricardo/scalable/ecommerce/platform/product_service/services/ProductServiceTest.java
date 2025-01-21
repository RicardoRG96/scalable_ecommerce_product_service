package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.BrandRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.unitTestData.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockitoBean
    ProductRepository productRepository;

    @MockitoBean
    CategoryRepository categoryRepository;

    @MockitoBean
    BrandRepository brandRepository;

    @Autowired
    ProductService productService;

    @Test
    void testFindById() {
        when(productRepository.findById(1L)).thenReturn(Data.createProduct001());
        when(productRepository.findById(2L)).thenReturn(Data.createProduct002());

        Optional<Product> product1 = productService.findById(1L);
        Optional<Product> product2 = productService.findById(2L);

        assertAll(
            () -> assertTrue(product1.isPresent()),
            () -> assertTrue(product2.isPresent()),
            () -> assertEquals(1L, product1.orElseThrow().getId()),
            () -> assertEquals(2L, product2.orElseThrow().getId()),
            () -> assertEquals("Notebook ASUS", product1.orElseThrow().getName()),
            () -> assertEquals("Macbook Apple", product2.orElseThrow().getName())
        );
    }

    @Test
    void testFindByName() {
        when(productRepository.findByName("Notebook ASUS")).thenReturn(Data.createProduct001());
        when(productRepository.findByName("Macbook Apple")).thenReturn(Data.createProduct002());

        Optional<Product> producOptional1 = productService.findByName("Notebook ASUS");
        Optional<Product> producOptional2 = productService.findByName("Macbook Apple");

        assertAll(
            () -> assertTrue(producOptional1.isPresent()),
            () -> assertTrue(producOptional2.isPresent()),
            () -> assertEquals(1L, producOptional1.orElseThrow().getId()),
            () -> assertEquals("Notebook ASUS", producOptional1.orElseThrow().getName()),
            () -> assertEquals(2L, producOptional2.orElseThrow().getId()),
            () -> assertEquals("Macbook Apple", producOptional2.orElseThrow().getName())
        );
    }

    @Test
    void testFindBySku() {
        when(productRepository.findBySku("123456")).thenReturn(Data.createProduct001());
        when(productRepository.findBySku("7891011")).thenReturn(Data.createProduct002());

        Optional<Product> producOptional1 = productService.findBySku("123456");
        Optional<Product> producOptional2 = productService.findBySku("7891011");

        assertAll(
            () -> assertTrue(producOptional1.isPresent()),
            () -> assertTrue(producOptional2.isPresent()),
            () -> assertEquals(1L, producOptional1.orElseThrow().getId()),
            () -> assertEquals("Notebook ASUS", producOptional1.orElseThrow().getName()),
            () -> assertEquals(2L, producOptional2.orElseThrow().getId()),
            () -> assertEquals("Macbook Apple", producOptional2.orElseThrow().getName())
        );
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(Data.createListOfProducts());

        List<Product> productsList = (List<Product>) productService.findAll();

        assertAll(
            () -> assertNotNull(productsList),
            () -> assertEquals(2, productsList.size()),
            () -> assertEquals(1L, productsList.get(0).getId()),
            () -> assertEquals(2L, productsList.get(1).getId()),
            () -> assertEquals("Notebook ASUS", productsList.get(0).getName()),
            () -> assertEquals("Macbook Apple", productsList.get(1).getName())
        );
    }

    @Test
    void testSave() {
        when(categoryRepository.findById(1L)).thenReturn(Data.createCategory001());
        when(brandRepository.findById(1L)).thenReturn(Data.createBrand001());
        when(productRepository.save(any())).thenReturn(Data.createProduct001().orElseThrow());

        ProductCreationDto productCreationRequest = new ProductCreationDto();
        productCreationRequest.setSku("123456");
        productCreationRequest.setUpc("abcde");
        productCreationRequest.setName("Notebook ASUS");
        productCreationRequest.setDescription("Computador de ultima generacion ASUS");
        productCreationRequest.setCategoryId(1L);
        productCreationRequest.setBrandId(1L);
        productCreationRequest.setPrice(1000.99);
        productCreationRequest.setStock(100);
        productCreationRequest.setImageUrl("image.png");
        productCreationRequest.setIsActive(true);
        productCreationRequest.setIsFeatured(true);
        productCreationRequest.setIsOnSale(false);

        Product createdProduct = productService.save(productCreationRequest).orElseThrow();

        assertAll(
            () -> assertNotNull(createdProduct),
            () -> assertEquals(1L, createdProduct.getId()),
            () -> assertEquals("Notebook ASUS", createdProduct.getName()),
            () -> assertEquals(1000.99, createdProduct.getPrice())
        );
    }

    @Test
    void testUpdate() {
        Product updatedProduct = Data.createProduct001().orElseThrow();
        updatedProduct.setName("Notebook ASUS i9");
        when(productRepository.findById(1L)).thenReturn(Data.createProduct001());
        when(productRepository.save(any())).thenReturn(updatedProduct);

        Optional<Product> productOptionalResponse = productService.update(updatedProduct, 1L);

        assertAll(
            () -> assertTrue(productOptionalResponse.isPresent()),
            () -> assertEquals(1L, productOptionalResponse.orElseThrow().getId()),
            () -> assertEquals("Notebook ASUS i9", productOptionalResponse.orElseThrow().getName()),
            () -> assertEquals(1000.99, productOptionalResponse.orElseThrow().getPrice())
        );
    }

}
