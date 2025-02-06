package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class ProductSkuServiceTest {

    @MockitoBean
    ProductSkuRepository productSkuRepository;

    @MockitoBean
    ProductRepository productRepository;

    @MockitoBean
    ProductAttributeRepository productAttributeRepository;

    @Autowired
    ProductSkuService productSkuService;

    @Test
    void testFindById() {
        when(productSkuRepository.findById(1L)).thenReturn(createProductSku001());
        when(productSkuRepository.findById(2L)).thenReturn(createProductSku002());

        Optional<ProductSku> productSku1 = productSkuService.findById(1L);
        Optional<ProductSku> productSku2 = productSkuService.findById(2L);

        assertAll(
            () -> assertTrue(productSku1.isPresent()),
            () -> assertTrue(productSku2.isPresent()),
            () -> assertEquals(1L, productSku1.orElseThrow().getId()),
            () -> assertEquals(2L, productSku2.orElseThrow().getId()),
            () -> assertEquals("SKU001", productSku1.orElseThrow().getSku()),
            () -> assertEquals("SKU002", productSku2.orElseThrow().getSku()),
            () -> assertEquals("negro", productSku1.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("azul", productSku2.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("none", productSku1.orElseThrow().getSizeAttribute().getValue()),
            () -> assertEquals("none", productSku2.orElseThrow().getSizeAttribute().getValue())
        );
    }

    @Test
    void testFindByProductId() {
        when(productSkuRepository.findByProductId(1L)).thenReturn(createListOfProductSkuByProductId1());
        when(productSkuRepository.findByProductId(4L)).thenReturn(createListOfProductSkuByProductId4());

        List<ProductSku> productsSkuWithProductId1 = (List<ProductSku>) productSkuService.findByProductId(1L);
        List<ProductSku> productsSkuWithProductId4 = (List<ProductSku>) productSkuService.findByProductId(4L);

        assertAll(
            () -> assertEquals(2, productsSkuWithProductId4.size()),
            () -> assertEquals("SKU004", productsSkuWithProductId4.get(0).getSku()),
            () -> assertEquals("SKU006", productsSkuWithProductId4.get(1).getSku()),
            () -> assertEquals("negro", productsSkuWithProductId4.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productsSkuWithProductId4.get(1).getColorAttribute().getValue()),
            () -> assertEquals("S", productsSkuWithProductId4.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("S", productsSkuWithProductId4.get(1).getSizeAttribute().getValue())
        );

        assertAll(
            () -> assertEquals(1, productsSkuWithProductId1.size()),
            () -> assertEquals("SKU001", productsSkuWithProductId1.get(0).getSku()),
            () -> assertEquals("negro", productsSkuWithProductId1.get(0).getColorAttribute().getValue()),
            () -> assertEquals("none", productsSkuWithProductId1.get(0).getSizeAttribute().getValue())
        );
    }

    @Test
    void testGetBySku() {
        when(productSkuRepository.findBySku("SKU005")).thenReturn(createProductSku005());

        Optional<ProductSku> searchedProductSku = productSkuService.findBySku("SKU005");

        assertAll(
            () -> assertTrue(searchedProductSku.isPresent()),
            () -> assertEquals(5L, searchedProductSku.orElseThrow().getId()),
            () -> assertEquals("SKU005", searchedProductSku.orElseThrow().getSku()),
            () -> assertEquals("azul", searchedProductSku.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("M", searchedProductSku.orElseThrow().getSizeAttribute().getValue())
        );
    }

    @Test
    void testGetBySkuAndIsActive() {
        when(productSkuRepository.findBySkuAndIsActive("SKU006", true)).thenReturn(createProductSku006());
        when(productSkuRepository.findBySkuAndIsActive("SKU007", false)).thenReturn(Optional.empty());

        Optional<ProductSku> searchedProductSkuActive = productSkuService.findBySkuAndIsActive("SKU006", true);
        Optional<ProductSku> searchedProductSkuInactive = productSkuService.findBySkuAndIsActive("SKU007", false);

        assertAll(
            () -> assertTrue(searchedProductSkuActive.isPresent()),
            () -> assertEquals(6L, searchedProductSkuActive.orElseThrow().getId()),
            () -> assertEquals("SKU006", searchedProductSkuActive.orElseThrow().getSku()),
            () -> assertEquals("rojo", searchedProductSkuActive.orElseThrow().getColorAttribute().getValue()),
            () -> assertEquals("S", searchedProductSkuActive.orElseThrow().getSizeAttribute().getValue())
        );

        assertAll(
            () -> assertFalse(searchedProductSkuInactive.isPresent()),
            () -> assertEquals(Optional.empty(), searchedProductSkuInactive)
        );
    }

    @Test
    void testFindByProductIdAndSizeAttributeId() {
        when(productSkuRepository.findByProductIdAndSizeAttributeId(4L, 4L))
            .thenReturn(createListOfProductSkuByProductIdAndSizeAttributeId());

        List<ProductSku> searchedProductsSku = 
            (List<ProductSku>) productSkuService.findByProductIdAndSizeAttributeId(4L, 4L);
        
        assertAll(
            () -> assertNotNull(searchedProductsSku),
            () -> assertEquals(4L, searchedProductsSku.get(0).getId()),
            () -> assertEquals(6L, searchedProductsSku.get(1).getId()),
            () -> assertEquals(4L, searchedProductsSku.get(0).getProduct().getId()),
            () -> assertEquals(4L, searchedProductsSku.get(1).getProduct().getId()),
            () -> assertEquals("SKU004", searchedProductsSku.get(0).getSku()),
            () -> assertEquals("SKU006", searchedProductsSku.get(1).getSku()),
            () -> assertEquals("negro", searchedProductsSku.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", searchedProductsSku.get(1).getColorAttribute().getValue())
        );
    }

    @Test
    void testFindByProductIdAndColorAttributeId() {
        when(productSkuRepository.findByProductIdAndColorAttributeId(5L, 2L))
            .thenReturn(createListOfProductSkuByProductIdAndColorAttributeId());

        List<ProductSku> searchedProductsSku = 
            (List<ProductSku>) productSkuService.findByProductIdAndColorAttributeId(5L, 2L);

        assertAll(
            () -> assertNotNull(searchedProductsSku),
            () -> assertEquals(5L, searchedProductsSku.get(0).getId()),
            () -> assertEquals(7L, searchedProductsSku.get(1).getId()),
            () -> assertEquals(5L, searchedProductsSku.get(0).getProduct().getId()),
            () -> assertEquals(5L, searchedProductsSku.get(1).getProduct().getId()),
            () -> assertEquals("SKU005", searchedProductsSku.get(0).getSku()),
            () -> assertEquals("SKU007", searchedProductsSku.get(1).getSku()),
            () -> assertEquals("M", searchedProductsSku.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("L", searchedProductsSku.get(1).getSizeAttribute().getValue())
        );
    }

    @Test
    void testFindByProductIdAndSizeAttributeIdAndColorAttributeId() {
        when(productSkuRepository.findByProductIdAndSizeAttributeIdAndColorAttributeId(1L, 7L, 3L))
            .thenReturn(createProductSku001());

        Optional<ProductSku> searchedProductsSku = 
            productSkuService.findByProductIdAndSizeAttributeIdAndColorAttributeId(1L, 7L, 3L);

        assertAll(
            () -> assertTrue(searchedProductsSku.isPresent()),
            () -> assertEquals(1L, searchedProductsSku.orElseThrow().getId()),
            () -> assertEquals(1L, searchedProductsSku.orElseThrow().getProduct().getId()),
            () -> assertEquals("SKU001", searchedProductsSku.orElseThrow().getSku()),
            () -> assertEquals("none", searchedProductsSku.orElseThrow().getSizeAttribute().getValue()),
            () -> assertEquals("negro", searchedProductsSku.orElseThrow().getColorAttribute().getValue())
        );
    }

    @Test
    void testFindAll() {
        when(productSkuRepository.findAll()).thenReturn(createListOfProductSku());

        List<ProductSku> productsSku = (List<ProductSku>) productSkuService.findAll();

        assertAll(
            () -> assertNotNull(productsSku),
            () -> assertEquals(7, productsSku.size()),
            () -> assertEquals(1L, productsSku.get(0).getId()),
            () -> assertEquals(2L, productsSku.get(1).getId()),
            () -> assertEquals(3L, productsSku.get(2).getId()),
            () -> assertEquals(4L, productsSku.get(3).getProduct().getId()),
            () -> assertEquals(5L, productsSku.get(4).getProduct().getId()),
            () -> assertEquals(4L, productsSku.get(5).getProduct().getId()),
            () -> assertEquals("SKU001", productsSku.get(0).getSku()),
            () -> assertEquals("SKU002", productsSku.get(1).getSku()),
            () -> assertEquals("SKU003", productsSku.get(2).getSku()),
            () -> assertEquals("none", productsSku.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("none", productsSku.get(1).getSizeAttribute().getValue()),
            () -> assertEquals("none", productsSku.get(2).getSizeAttribute().getValue()),
            () -> assertEquals("negro", productsSku.get(0).getColorAttribute().getValue()),
            () -> assertEquals("azul", productsSku.get(1).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productsSku.get(2).getColorAttribute().getValue())
        );
    }

}
