package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.unitTestData.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class AlgoliaConfigTest {

    @MockitoBean
    private ProductRepository productRepository;

    @Test
    void testPreparedData() {
        when(productRepository.findAll()).thenReturn(Data.createListOfProducts());

        List<Map<String, Object>> preparedData = Data.createListOfProductsMap();

        assertAll(
            () -> assertEquals(2, preparedData.size()),
            () -> assertNotNull(preparedData),
            () -> assertNotEquals(List.of(), preparedData),
            () -> assertEquals("Notebook ASUS", preparedData.get(0).get("name")),
            () -> assertEquals("Macbook Apple", preparedData.get(1).get("name"))
        );
    }

}
