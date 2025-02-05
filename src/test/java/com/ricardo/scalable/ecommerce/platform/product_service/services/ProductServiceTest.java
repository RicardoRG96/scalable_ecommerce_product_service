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

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.CategoryServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.BrandServiceTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
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
        when(productRepository.findById(1L)).thenReturn(createProduct001());
        when(productRepository.findById(2L)).thenReturn(createProduct002());

        Optional<Product> product1 = productService.findById(1L);
        Optional<Product> product2 = productService.findById(2L);

        assertAll(
            () -> assertTrue(product1.isPresent()),
            () -> assertTrue(product2.isPresent()),
            () -> assertEquals(1L, product1.orElseThrow().getId()),
            () -> assertEquals(2L, product2.orElseThrow().getId()),
            () -> assertEquals("Notebook Samsung", product1.orElseThrow().getName()),
            () -> assertEquals("iPhone Apple", product2.orElseThrow().getName()),
            () -> assertEquals("Computadoras", product1.orElseThrow().getCategory().getName()),
            () -> assertEquals("Celulares", product2.orElseThrow().getCategory().getName()),
            () -> assertEquals("Samsung", product1.orElseThrow().getBrand().getName()),
            () -> assertEquals("Apple", product2.orElseThrow().getBrand().getName())
        );
    }

    @Test
    void testFindByName() {
        when(productRepository.findByName("Notebook Samsung")).thenReturn(createProduct001());
        when(productRepository.findByName("Macbook Apple")).thenReturn(createProduct002());

        Optional<Product> producOptional1 = productService.findByName("Notebook Samsung");
        Optional<Product> producOptional2 = productService.findByName("Macbook Apple");

        assertAll(
            () -> assertTrue(producOptional1.isPresent()),
            () -> assertTrue(producOptional2.isPresent()),
            () -> assertEquals(1L, producOptional1.orElseThrow().getId()),
            () -> assertEquals("Notebook Samsung", producOptional1.orElseThrow().getName()),
            () -> assertEquals(2L, producOptional2.orElseThrow().getId()),
            () -> assertEquals("iPhone Apple", producOptional2.orElseThrow().getName()),
            () -> assertEquals("Computadoras", producOptional1.orElseThrow().getCategory().getName()),
            () -> assertEquals("Celulares", producOptional2.orElseThrow().getCategory().getName()),
            () -> assertEquals("Samsung", producOptional1.orElseThrow().getBrand().getName()),
            () -> assertEquals("Apple", producOptional2.orElseThrow().getBrand().getName())
        );
    }

    @Test
    void testFindAll() {
        when(productRepository.findAll()).thenReturn(createListOfProducts());

        List<Product> productsList = (List<Product>) productService.findAll();

        assertAll(
            () -> assertNotNull(productsList),
            () -> assertEquals(5, productsList.size()),
            () -> assertEquals(1L, productsList.get(0).getId()),
            () -> assertEquals(2L, productsList.get(1).getId()),
            () -> assertEquals(3L, productsList.get(2).getId()),
            () -> assertEquals(4L, productsList.get(3).getId()),
            () -> assertEquals("Notebook Samsung", productsList.get(0).getName()),
            () -> assertEquals("iPhone Apple", productsList.get(1).getName()),
            () -> assertEquals("Computadoras", productsList.get(0).getCategory().getName()),
            () -> assertEquals("Celulares", productsList.get(1).getCategory().getName()),
            () -> assertEquals("Samsung", productsList.get(0).getBrand().getName()),
            () -> assertEquals("Apple", productsList.get(1).getBrand().getName())
        );
    }

    @Test
    void testSave() {
        Product productCreationResponse = new Product();
        productCreationResponse.setId(6L);
        productCreationResponse.setName("Samsung Galaxy S23");
        productCreationResponse.setDescription("Celular de ultima generacion Samsung");
        productCreationResponse.setCategory(createSubCategory002().orElseThrow());
        productCreationResponse.setBrand(createBrand002().orElseThrow());
        productCreationResponse.setCover("image3.png");
        productCreationResponse.setCreatedAt(Timestamp.from(Instant.now()));
        productCreationResponse.setUpdatedAt(Timestamp.from(Instant.now()));

        when(categoryRepository.findById(5L)).thenReturn(createSubCategory002());
        when(brandRepository.findById(2L)).thenReturn(createBrand002());
        when(productRepository.save(any())).thenReturn(productCreationResponse);

        ProductCreationDto productCreationRequest = new ProductCreationDto();
        productCreationRequest.setName("Samsung Galaxy S23");
        productCreationRequest.setDescription("Celular de ultima generacion Samsung");
        productCreationRequest.setCategoryId(5L);
        productCreationRequest.setBrandId(2L);
        productCreationRequest.setCover("image3.png");

        Optional<Product> createdProduct = productService.save(productCreationRequest);

        assertAll(
            () -> assertTrue(createdProduct.isPresent()),
            () -> assertEquals(6L, createdProduct.orElseThrow().getId()),
            () -> assertEquals("Samsung Galaxy S23", createdProduct.orElseThrow().getName()),
            () -> assertEquals("Celulares", createdProduct.orElseThrow().getCategory().getName()),
            () -> assertEquals("Samsung", createdProduct.orElseThrow().getBrand().getName())
        );
    }

    @Test
    void testUpdate() {
        Product updatedProduct = createProduct001().orElseThrow();
        updatedProduct.setName("Notebook Samsung i9");

        when(productRepository.findById(1L)).thenReturn(createProduct001());
        when(productRepository.save(any())).thenReturn(updatedProduct);

        Optional<Product> productOptionalResponse = productService.update(updatedProduct, 1L);

        assertAll(
            () -> assertTrue(productOptionalResponse.isPresent()),
            () -> assertEquals(1L, productOptionalResponse.orElseThrow().getId()),
            () -> assertEquals("Notebook Samsung i9", productOptionalResponse.orElseThrow().getName()),
            () -> assertEquals("Computadoras", productOptionalResponse.orElseThrow().getCategory().getName()),
            () -> assertEquals("Samsung", productOptionalResponse.orElseThrow().getBrand().getName())
        );
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).deleteById(1L);

        productService.delete(1L);

        verify(productRepository, times(1)).deleteById(1L);
    }

}
