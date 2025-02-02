package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.unitTestData.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
public class AlgoliaConfigTest {

    @MockitoBean
    private ProductRepository productRepository;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

    @Test
    void testPreparedData() {
        Optional<Product> product1 = Data.createProduct001();
        Optional<Product> product2 = Data.createProduct002();
        Optional<Product> product3 = Data.createProduct003();
        Optional<Product> product4 = Data.createProduct004();
        Optional<Product> product5 = Data.createProduct005();

        when(productSkuRepository.findAll()).thenReturn(Data.createListOfProductSkus());
        when(productRepository.findById(1L)).thenReturn(product1);
        when(productRepository.findById(2L)).thenReturn(product2);
        when(productRepository.findById(3L)).thenReturn(product3);
        when(productRepository.findById(4L)).thenReturn(product4);
        when(productRepository.findById(5L)).thenReturn(product5);


        List<Map<String, Object>> preparedData = Data.createListOfProductsSkuMap();

        assertAll(
            () -> assertEquals(7, preparedData.size()),
            () -> assertNotNull(preparedData),
            () -> assertNotEquals(List.of(), preparedData),
            () -> assertEquals("Notebook Samsung", preparedData.get(0).get("name")),
            () -> assertEquals("iPhone Apple", preparedData.get(1).get("name")),
            () -> assertEquals("Notebook Lenovo", preparedData.get(2).get("name")),
            () -> assertEquals("Polera manga corta", preparedData.get(3).get("name")),
            () -> assertEquals("Jeans Americanino", preparedData.get(4).get("name")),
            () -> assertEquals("Polera manga corta", preparedData.get(5).get("name")),
            () -> assertEquals("Jeans Americanino", preparedData.get(6).get("name")),
            () -> assertEquals("Computadoras", preparedData.get(0).get("category")),
            () -> assertEquals("Celulares", preparedData.get(1).get("category")),
            () -> assertEquals("Computadoras", preparedData.get(2).get("category")),
            () -> assertEquals("Poleras", preparedData.get(3).get("category")),
            () -> assertEquals("Pantalones", preparedData.get(4).get("category")),
            () -> assertEquals("Poleras", preparedData.get(5).get("category")),
            () -> assertEquals("Pantalones", preparedData.get(6).get("category")),
            () -> assertEquals("none", preparedData.get(0).get("size")),
            () -> assertEquals("none", preparedData.get(1).get("size")),
            () -> assertEquals("none", preparedData.get(2).get("size")),
            () -> assertEquals("S", preparedData.get(3).get("size")),
            () -> assertEquals("M", preparedData.get(4).get("size")),
            () -> assertEquals("M", preparedData.get(5).get("size")),
            () -> assertEquals("S", preparedData.get(6).get("size")),
            () -> assertEquals("azul", preparedData.get(0).get("color")),
            () -> assertEquals("azul", preparedData.get(1).get("color")),
            () -> assertEquals("azul", preparedData.get(2).get("color")),
            () -> assertEquals("azul", preparedData.get(3).get("color")),
            () -> assertEquals("negro", preparedData.get(4).get("color")),
            () -> assertEquals("negro", preparedData.get(5).get("color")),
            () -> assertEquals("azul", preparedData.get(6).get("color"))
        );
    }
    
}
