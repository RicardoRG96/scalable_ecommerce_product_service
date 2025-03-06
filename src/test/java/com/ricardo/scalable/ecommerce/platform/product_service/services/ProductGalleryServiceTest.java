package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductGalleryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.storageService.StorageService;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductGalleryServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.io.IOException;
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

    @MockitoBean
    private StorageService storageService;

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

    @Test
    void testFindByProductId() {
        Optional<List<ProductGallery>> productsGallery = createListOfProductGalleryByProductId4();
        when(productGalleryRepository.findByProductId(4L)).thenReturn(productsGallery);

        Optional<List<ProductGallery>> result = productGalleryService.findByProductId(4L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertFalse(result.orElseThrow().isEmpty()),
            () -> assertEquals(4L, result.orElseThrow().get(0).getId()),
            () -> assertEquals(6L, result.orElseThrow().get(1).getId()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().get(0).getProduct().getName()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().get(1).getProduct().getName()),
            () -> assertEquals("negro", result.orElseThrow().get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", result.orElseThrow().get(1).getColorAttribute().getValue()),
            () -> assertEquals("https://example.com/image4.png", result.orElseThrow().get(0).getImageUrl()),
            () -> assertEquals("https://example.com/image6.png", result.orElseThrow().get(1).getImageUrl())
        );
    }

    @Test
    void testFindByColorAttributeId() {
        Optional<List<ProductGallery>> productsGallery = createListOfProductGalleryByColorAttributeId1();
        when(productGalleryRepository.findByColorAttributeId(1L)).thenReturn(productsGallery);

        Optional<List<ProductGallery>> result = productGalleryService.findByColorAttributeId(1L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertFalse(result.orElseThrow().isEmpty()),
            () -> assertEquals(3L, result.orElseThrow().get(0).getId()),
            () -> assertEquals(6L, result.orElseThrow().get(1).getId()),
            () -> assertEquals("MacBook Apple", result.orElseThrow().get(0).getProduct().getName()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().get(1).getProduct().getName()),
            () -> assertEquals("rojo", result.orElseThrow().get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", result.orElseThrow().get(1).getColorAttribute().getValue()),
            () -> assertEquals("https://example.com/image3.png", result.orElseThrow().get(0).getImageUrl()),
            () -> assertEquals("https://example.com/image6.png", result.orElseThrow().get(1).getImageUrl())
        );
    }

    @Test
    void testFindByProductIdAndColorAttributeId() {
        Optional<ProductGallery> productGallery = createProductGallery004();
        when(productGalleryRepository.findByProductIdAndColorAttributeId(4L, 3L)).thenReturn(productGallery);

        Optional<ProductGallery> result = productGalleryService.findByProductIdAndColorAttributeId(4L, 3L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(4L, result.orElseThrow().getId()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("https://example.com/image4.png", result.orElseThrow().getImageUrl())
        );
    }

    @Test
    void testFindAll() {
        List<ProductGallery> productsGallery = (List<ProductGallery>) createListOfProductGallery();
        when(productGalleryRepository.findAll()).thenReturn(productsGallery);

        List<ProductGallery> result = (List<ProductGallery>) productGalleryService.findAll();

        assertAll(
            () -> assertNotNull(result),
            () -> assertFalse(result.isEmpty()),
            () -> assertEquals(1L, result.get(0).getId()),
            () -> assertEquals(2L, result.get(1).getId()),
            () -> assertEquals(3L, result.get(2).getId()),
            () -> assertEquals("Notebook Samsung", result.get(0).getProduct().getName()),
            () -> assertEquals("iPhone Apple", result.get(1).getProduct().getName()),
            () -> assertEquals("MacBook Apple", result.get(2).getProduct().getName()),
            () -> assertEquals("https://example.com/image1.png", result.get(0).getImageUrl()),
            () -> assertEquals("https://example.com/image2.png", result.get(1).getImageUrl()),
            () -> assertEquals("https://example.com/image3.png", result.get(2).getImageUrl())
        );
    }

    @Test
    void testSave() throws IOException {
        ProductGalleryCreationDto productGalleryCreationDto = createProductGalleryCreationDto();
        ProductGallery productGallery = createProductGalleryCreationResponse();
        Optional<String> imageStoredUrl = 
            Optional.of("https://product-gallery-images-ecommerce.s3.us-east-2.amazonaws.com/2025.03.02.13.39.01_polera-manga-corta.png");

        when(productRepository.findByName("Polera manga corta")).thenReturn(createProduct004());
        when(productAttributeRepository.findByValue("negro")).thenReturn(createProductAttribute003());
        when(storageService.store(any())).thenReturn(imageStoredUrl);
        when(storageService.getImageUrl(any())).thenReturn(imageStoredUrl.orElseThrow());
        when(productGalleryRepository.save(any())).thenReturn(productGallery);

        Optional<ProductGallery> result = productGalleryService.save(productGalleryCreationDto);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(8L, result.orElseThrow().getId()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("negro", result.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("https://product-gallery-images-ecommerce.s3.us-east-2.amazonaws.com/2025.03.02.13.39.01_polera-manga-corta.png", 
                result.orElseThrow().getImageUrl()
            )
        );
    }

    @Test
    void testUpdate() throws IOException {
        ProductGalleryCreationDto updatedProductGallery = createUpdatedProductGallery();
        ProductGallery updatedProductGalleryResponse = createProductGalleryUpdateResponse();
        Optional<String> imageStoredUrl = 
            Optional.of("https://product-gallery-images-ecommerce.s3.us-east-2.amazonaws.com/2025.03.02.13.39.01_polera-manga-corta2.png");

        when(productGalleryRepository.findById(8L)).thenReturn(Optional.of(createProductGalleryCreationResponse()));
        when(productRepository.findByName("Polera manga corta")).thenReturn(createProduct005());
        when(productAttributeRepository.findByValue("rojo")).thenReturn(createProductAttribute002());
        when(storageService.store(any())).thenReturn(imageStoredUrl);
        when(storageService.getImageUrl(any())).thenReturn(imageStoredUrl.orElseThrow());
        when(productGalleryRepository.save(any())).thenReturn(updatedProductGalleryResponse);

        Optional<ProductGallery> result = productGalleryService.update(updatedProductGallery, 8L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(8L, result.orElseThrow().getId()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("rojo", result.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals(
                "https://product-gallery-images-ecommerce.s3.us-east-2.amazonaws.com/2025.03.02.13.39.01_polera-manga-corta2.png", 
                result.orElseThrow().getImageUrl()
            )
        );
    }

    @Test
    void testDelete() {
        doNothing().when(productGalleryRepository).deleteById(1L);

        productGalleryService.delete(1L);

        verify(productGalleryRepository, times(1)).deleteById(1L);
    }

}
