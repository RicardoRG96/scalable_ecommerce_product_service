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
        client.get()
                .uri("/search?query=balon")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Nike", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU9820", json.get(0).path("sku").asText()),
                            () -> assertEquals("Futbol", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(29.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductsByCategory() {
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
                            () -> assertEquals(2L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Notebook de ultima generacion", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("ASUS", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2501", json.get(0).path("sku").asText()),
                            () -> assertEquals("Notebooks", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(999.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductsByBrand() {
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
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Apple", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2210", json.get(0).path("sku").asText()),
                            () -> assertEquals("Smartphone", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(1500.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchProductBySku() {
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
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Apple", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2210", json.get(0).path("sku").asText()),
                            () -> assertEquals("Smartphone", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(1500.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByOneBrand() {
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
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Apple", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2210", json.get(0).path("sku").asText()),
                            () -> assertEquals("Smartphone", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(1500.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByMultipleBrands() {
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
                            () -> assertEquals(2L, json.get(0).path("id").asLong()),
                            () -> assertEquals(3L, json.get(1).path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon premier league 2025", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("Notebook de ultima generacion", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(1).path("product").path("description").asText()),
                            () -> assertEquals("ASUS", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("Nike", json.get(1).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2501", json.get(0).path("sku").asText()),
                            () -> assertEquals("SKU9820", json.get(1).path("sku").asText()),
                            () -> assertEquals("Notebooks", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals("Futbol", json.get(1).path("product").path("category").path("name").asText()),
                            () -> assertEquals(999.99, json.get(0).path("price").asDouble()),
                            () -> assertEquals(29.99, json.get(1).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByOneCategory() {
        client.get()
                .uri("/category?category=Smartphone")
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
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Apple", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2210", json.get(0).path("sku").asText()),
                            () -> assertEquals("Smartphone", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(1500.99, json.get(0).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByMultipleCategories() {
        client.get()
                .uri("/category?category=Notebooks&category=Futbol")
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
                            () -> assertEquals(2L, json.get(0).path("id").asLong()),
                            () -> assertEquals(3L, json.get(1).path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon premier league 2025", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("Notebook de ultima generacion", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(1).path("product").path("description").asText()),
                            () -> assertEquals("ASUS", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("Nike", json.get(1).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2501", json.get(0).path("sku").asText()),
                            () -> assertEquals("SKU9820", json.get(1).path("sku").asText()),
                            () -> assertEquals("Notebooks", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals("Futbol", json.get(1).path("product").path("category").path("name").asText()),
                            () -> assertEquals(999.99, json.get(0).path("price").asDouble()),
                            () -> assertEquals(29.99, json.get(1).path("price").asDouble())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testFilterByPriceRange() {
        client.get()
                .uri("/price?minPrice=900&maxPrice=1600")
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
                            () -> assertEquals(2L, json.get(0).path("id").asLong()),
                            () -> assertEquals(1L, json.get(1).path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("iPhone 15", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("Notebook de ultima generacion", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(1).path("product").path("description").asText()),
                            () -> assertEquals("ASUS", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("Apple", json.get(1).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU2501", json.get(0).path("sku").asText()),
                            () -> assertEquals("SKU2210", json.get(1).path("sku").asText()),
                            () -> assertEquals("Notebooks", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals("Smartphone", json.get(1).path("product").path("category").path("name").asText()),
                            () -> assertEquals(999.99, json.get(0).path("price").asDouble()),
                            () -> assertEquals(1500.99, json.get(1).path("price").asDouble())
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchWithStopWords() {
        client.get()
                .uri("/search?query=el balon")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Nike", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU9820", json.get(0).path("sku").asText()),
                            () -> assertEquals("Futbol", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(29.99, json.get(0).path("price").asDouble())

                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchWithSynonyms() {
        client.get()
                .uri("/search?query=pelota")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Nike", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU9820", json.get(0).path("sku").asText()),
                            () -> assertEquals("Futbol", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(29.99, json.get(0).path("price").asDouble())

                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                client.get()
                .uri("/search?query=esferico")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Nike", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU9820", json.get(0).path("sku").asText()),
                            () -> assertEquals("Futbol", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(29.99, json.get(0).path("price").asDouble())
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    void testSearchWithIgnorePlurals() {
        client.get()
                .uri("/search?query=balones")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Balon oficial Premier League", json.get(0).path("product").path("description").asText()),
                            () -> assertEquals("Nike", json.get(0).path("product").path("brand").path("name").asText()),
                            () -> assertEquals("SKU9820", json.get(0).path("sku").asText()),
                            () -> assertEquals("Futbol", json.get(0).path("product").path("category").path("name").asText()),
                            () -> assertEquals(29.99, json.get(0).path("price").asDouble())

                        );
                    } catch (Exception ex) {
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
            () -> assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url")),
            () -> assertEquals("products_test", env.getProperty("algolia.indexName"))
        );
    }

}
