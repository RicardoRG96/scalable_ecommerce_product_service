// package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;

// import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
// import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
// import com.ricardo.scalable.ecommerce.platform.product_service.unitTestData.Data;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.mockito.Mockito.*;

// import java.util.List;

// @SpringBootTest
// public class SearchServiceClientImplTest {

//     @MockitoBean
//     private SearchService searchService;

//     @Autowired
//     private SearchServiceClient searchServiceClient;

//     @Test
//     void testSearchProducts() {
//         when(searchService.searchProducts("Notebook")).thenReturn(Data.createListOfProductsForSearch());

//         List<ProductSku> productSearchResponse = searchServiceClient.searchProducts("Notebook");

//         assertAll(
//             () -> assertNotNull(productSearchResponse),
//             () -> assertEquals(2, productSearchResponse.size()),
//             () -> assertEquals("Notebook ASUS", productSearchResponse.get(0).getName()),
//             () -> assertEquals("Notebook Lenovo", productSearchResponse.get(1).getName())
//         );
//     }

//     @Test
//     void testFilterByBrand() {
//         when(searchService.filterByBrand(List.of("Samsung"))).thenReturn(Data.createListOfFilterByBrand());

//         List<ProductSku> productSearchResponse = searchServiceClient.filterByBrand(List.of("Samsung"));

//         assertAll(
//             () -> assertNotNull(productSearchResponse),
//             () -> assertEquals(2, productSearchResponse.size()),
//             () -> assertEquals("Notebook ASUS", productSearchResponse.get(0).getName()),
//             () -> assertEquals("Macbook Apple", productSearchResponse.get(1).getName())
//         );   
//     }

//     @Test
//     void testFilterByCategory() {
//         when(searchService.filterByCategory(List.of("Deportes"))).thenReturn(Data.createListOfFilterByCategory());

//         List<ProductSku> productSearchResponse = searchServiceClient.filterByCategory(List.of("Deportes"));

//         assertAll(
//             () -> assertNotNull(productSearchResponse),
//             () -> assertEquals(2, productSearchResponse.size()),
//             () -> assertEquals("Macbook Apple", productSearchResponse.get(0).getName()),
//             () -> assertEquals("Notebook Lenovo", productSearchResponse.get(1).getName())
//         );
//     }

//     @Test
//     void testFilterByPriceRange() {
//         when(searchService.filterByPriceRange(1000.0, 1300.0)).thenReturn(Data.createListOfFilterByPriceRange());

//         List<ProductSku> productSearchResponse = searchServiceClient.filterByPriceRange(1000.0, 1300.0);

//         assertAll(
//             () -> assertNotNull(productSearchResponse),
//             () -> assertEquals(2, productSearchResponse.size()),
//             () -> assertEquals("Notebook ASUS", productSearchResponse.get(0).getName()),
//             () -> assertEquals("Macbook Apple", productSearchResponse.get(1).getName())
//         );
//     }

// }
