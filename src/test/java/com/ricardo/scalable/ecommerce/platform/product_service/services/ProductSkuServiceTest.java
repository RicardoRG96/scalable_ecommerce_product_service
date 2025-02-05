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
            () -> assertEquals("L", productsSkuWithProductId4.get(1).getSizeAttribute().getValue())
        );

        assertAll(
            () -> assertEquals(1, productsSkuWithProductId1.size()),
            () -> assertEquals("SKU001", productsSkuWithProductId1.get(0).getSku()),
            () -> assertEquals("negro", productsSkuWithProductId1.get(0).getColorAttribute().getValue()),
            () -> assertEquals("none", productsSkuWithProductId1.get(0).getSizeAttribute().getValue())
        );
    }

}
