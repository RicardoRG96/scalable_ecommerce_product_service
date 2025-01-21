package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.integrationTestData.Data;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SearchEndpointsTest {

    @Autowired
    private Environment env;
    
    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSearchProductsByProductName() {
        Product product3 = Data.createProduct003();

        client.get()
                .uri("/search?query=camiseta")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product3.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product3.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product3.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product3.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product3.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product3.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product3.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product3.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductsByCategory() {
        Product product2 = Data.createProduct002();

        client.get()
                .uri("/search?query=Notebooks")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product2.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product2.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product2.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product2.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product2.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product2.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product2.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product2.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductsByBrand() {
        Product product1 = Data.createProduct001();

        client.get()
                .uri("/search?query=Apple")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product1.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product1.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductBySku() {
        Product product1 = Data.createProduct001();

        client.get()
                .uri("/search?query=SKU2210")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product1.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product1.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void searchProductByUpc() {
        Product product1 = Data.createProduct001();

        client.get()
                .uri("/search?query=UPC6569")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product1.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product1.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByOneBrand() {
        Product product1 = Data.createProduct001();
        Product product2 = Data.createProduct002();
        Product product3 = Data.createProduct003();

        client.get()
                .uri("/brand?brand=Apple")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1, json.size()),
                            () -> assertEquals(product1.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product1.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByMultipleBrands() {
        Product product2 = Data.createProduct002();
        Product product3 = Data.createProduct003();

        client.get()
                .uri("/brand?brand=asus&brand=nike")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(2, json.size()),
                            () -> assertEquals(product2.getId(), json.get(0).path("id").asLong()),
                            () -> assertEquals(product3.getId(), json.get(1).path("id").asLong()),
                            () -> assertEquals(product2.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product3.getName(), json.get(1).path("name").asText()),
                            () -> assertEquals(product2.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product3.getDescription(), json.get(1).path("description").asText()),
                            () -> assertEquals(product2.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product3.getBrand().getName(), json.get(1).path("brand").path("name").asText()),
                            () -> assertEquals(product2.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product3.getSku(), json.get(1).path("sku").asText()),
                            () -> assertEquals(product2.getUpc(), json.get(0).path("upc").asText()),
                            () -> assertEquals(product3.getUpc(), json.get(1).path("upc").asText()),
                            () -> assertEquals(product2.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product3.getCategory().getName(), json.get(1).path("category").path("name").asText()),
                            () -> assertEquals(product2.getPrice(), json.get(0).path("price").asDouble()),
                            () -> assertEquals(product3.getPrice(), json.get(1).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertyFile() {
        assertAll(
            () -> assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url")),
            () -> assertEquals("products_test", env.getProperty("algolia.indexName"))
        );
    }

}
