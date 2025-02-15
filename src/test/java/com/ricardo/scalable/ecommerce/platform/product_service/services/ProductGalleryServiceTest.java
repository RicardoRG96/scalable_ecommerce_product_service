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
    void testSave() {
        ProductGalleryCreationDto productGalleryCreationDto = createProductGalleryCreationDto();
        ProductGallery productGallery = createProductGalleryCreationResponse();

        when(productRepository.findById(4L)).thenReturn(createProduct004());
        when(productAttributeRepository.findById(3L)).thenReturn(createProductAttribute003());
        when(productGalleryRepository.save(any())).thenReturn(productGallery);

        Optional<ProductGallery> result = productGalleryService.save(productGalleryCreationDto);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(8L, result.orElseThrow().getId()),
            () -> assertEquals("Polera manga corta", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("negro", result.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("https://example.com/image8.png", result.orElseThrow().getImageUrl())
        );
    }

    @Test
    void testUpdate() {
        ProductGallery updatedProductGallery = createUpdatedProductGallery();

        when(productGalleryRepository.findById(7L)).thenReturn(createProductGallery007());
        when(productGalleryRepository.save(any())).thenReturn(updatedProductGallery);

        Optional<ProductGallery> result = productGalleryService.update(updatedProductGallery, 7L);

        assertAll(
            () -> assertTrue(result.isPresent()),
            () -> assertEquals(7L, result.orElseThrow().getId()),
            () -> assertEquals("Jeans Americanino", result.orElseThrow().getProduct().getName()),
            () -> assertEquals("azul", result.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("https://example.com/image8-blue.png", result.orElseThrow().getImageUrl())
        );
    }

    @Test
    void testDelete() {
        doNothing().when(productGalleryRepository).deleteById(1L);

        productGalleryService.delete(1L);

        verify(productGalleryRepository, times(1)).deleteById(1L);
    }

}
