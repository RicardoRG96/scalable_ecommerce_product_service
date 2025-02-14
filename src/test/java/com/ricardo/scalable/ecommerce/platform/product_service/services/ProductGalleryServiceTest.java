package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductGalleryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductGalleryServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class ProductGalleryServiceTest {

    @MockitoBean
    private ProductGalleryRepository productGalleryRepository;

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductGalleryService productGalleryService;

    @Test
    void testFindById() {
        Optional<ProductGallery> productGallery = createProductGallery001();
        when(productGalleryRepository.findById(1L)).thenReturn(productGallery);

        Optional<ProductGallery> result = productGalleryService.findById(1L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(1L, result.orElseThrow().getId()),
            () -> assertEquals("Notebook Samsung", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("negro", result.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("https://example.com/image1.png", result.orElseThrow().getImageUrl())
        );
    }



}
