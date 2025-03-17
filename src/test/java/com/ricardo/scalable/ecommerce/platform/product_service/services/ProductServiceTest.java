package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
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

        Optional<Product> product1 = productService.findById(1L);

        assertAll(
            () -> assertTrue(product1.isPresent()),
            () -> assertEquals(1L, product1.orElseThrow().getId()),
            () -> assertEquals("Notebook Samsung", product1.orElseThrow().getName()),
            () -> assertEquals("Computadoras", product1.orElseThrow().getCategory().getName()),
            () -> assertEquals("Samsung", product1.orElseThrow().getBrand().getName())
        );
    }

    @Test
    void testFindByName() {
        when(productRepository.findByName("Macbook Apple")).thenReturn(createProduct002());
        
        Optional<Product> producOptional = productService.findByName("Macbook Apple");

        assertAll(
            () -> assertTrue(producOptional.isPresent()),
            () -> assertEquals(2L, producOptional.orElseThrow().getId()),
            () -> assertEquals("iPhone Apple", producOptional.orElseThrow().getName()),
            () -> assertEquals("Celulares", producOptional.orElseThrow().getCategory().getName()),
            () -> assertEquals("Apple", producOptional.orElseThrow().getBrand().getName())
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
        ProductCreationDto productCreationRequest = createProductCreationDto();
        Product productCreationResponse = createProductCreationResponse();

        when(categoryRepository.findById(5L)).thenReturn(createSubCategory002());
        when(brandRepository.findById(2L)).thenReturn(createBrand002());
        when(productRepository.save(any())).thenReturn(productCreationResponse);

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
