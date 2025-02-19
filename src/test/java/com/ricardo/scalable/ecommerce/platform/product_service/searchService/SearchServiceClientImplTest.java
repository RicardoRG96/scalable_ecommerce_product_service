package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.SearchServiceClientTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

@SpringBootTest
public class SearchServiceClientImplTest {

    @MockitoBean
    private SearchService searchService;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @Test
    void testSearchProducts() {
        when(searchService.searchProducts("Notebook")).thenReturn(createListOfProductsForSearch());

        List<ProductSku> productSearchResponse = searchServiceClient.searchProducts("Notebook");

        assertAll(
            () -> assertNotNull(productSearchResponse),
            () -> assertEquals(2, productSearchResponse.size()),
            () -> assertEquals("Notebook Samsung", productSearchResponse.get(0).getProduct().getName()),
            () -> assertEquals("MacBook Apple", productSearchResponse.get(1).getProduct().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(0).getProduct().getCategory().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(1).getProduct().getCategory().getName()),
            () -> assertEquals("none-size", productSearchResponse.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("none-size", productSearchResponse.get(1).getSizeAttribute().getValue()),
            () -> assertEquals("negro", productSearchResponse.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productSearchResponse.get(1).getColorAttribute().getValue())
        );
    }

    @Test
    void testFilterByBrand() {
        when(searchService.filterByBrand(List.of("Apple"))).thenReturn(createListOfFilterByBrand());

        List<ProductSku> productSearchResponse = searchServiceClient.filterByBrand(List.of("Apple"));

        assertAll(
            () -> assertNotNull(productSearchResponse),
            () -> assertEquals(2, productSearchResponse.size()),
            () -> assertEquals("iPhone Apple", productSearchResponse.get(0).getProduct().getName()),
            () -> assertEquals("MacBook Apple", productSearchResponse.get(1).getProduct().getName()),
            () -> assertEquals("Celulares", productSearchResponse.get(0).getProduct().getCategory().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(1).getProduct().getCategory().getName()),
            () -> assertEquals("none-size", productSearchResponse.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("none-size", productSearchResponse.get(1).getSizeAttribute().getValue()),
            () -> assertEquals("azul", productSearchResponse.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productSearchResponse.get(1).getColorAttribute().getValue())
        );   
    }

    @Test
    void testFilterByCategory() {
        when(searchService.filterByCategory(List.of("Computadoras"))).thenReturn(createListOfFilterByCategory());

        List<ProductSku> productSearchResponse = searchServiceClient.filterByCategory(List.of("Computadoras"));

        assertAll(
            () -> assertNotNull(productSearchResponse),
            () -> assertEquals(2, productSearchResponse.size()),
            () -> assertEquals("Notebook Samsung", productSearchResponse.get(0).getProduct().getName()),
            () -> assertEquals("MacBook Apple", productSearchResponse.get(1).getProduct().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(0).getProduct().getCategory().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(1).getProduct().getCategory().getName()),
            () -> assertEquals("none-size", productSearchResponse.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("none-size", productSearchResponse.get(1).getSizeAttribute().getValue()),
            () -> assertEquals("negro", productSearchResponse.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productSearchResponse.get(1).getColorAttribute().getValue())
        );
    }

    @Test
    void testFilterByPriceRange() {
        when(searchService.filterByPriceRange(1000.0, 1300.0)).thenReturn(createListOfFilterByPriceRange());

        List<ProductSku> productSearchResponse = searchServiceClient.filterByPriceRange(1000.0, 1300.0);

        assertAll(
            () -> assertNotNull(productSearchResponse),
            () -> assertEquals(2, productSearchResponse.size()),
            () -> assertEquals("Notebook Samsung", productSearchResponse.get(0).getProduct().getName()),
            () -> assertEquals("MacBook Apple", productSearchResponse.get(1).getProduct().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(0).getProduct().getCategory().getName()),
            () -> assertEquals("Computadoras", productSearchResponse.get(1).getProduct().getCategory().getName()),
            () -> assertEquals("none-size", productSearchResponse.get(0).getSizeAttribute().getValue()),
            () -> assertEquals("none-size", productSearchResponse.get(1).getSizeAttribute().getValue()),
            () -> assertEquals("negro", productSearchResponse.get(0).getColorAttribute().getValue()),
            () -> assertEquals("rojo", productSearchResponse.get(1).getColorAttribute().getValue())
        );
    }

}
