package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductGalleryControllerTest {

    @Autowired
    private Environment env;

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetById() {
        client.get()
                .uri("/product-gallery/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/iphone15-black.jpg", json.path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetByProductId() {
        client.get()
                .uri("/product-gallery/product/4")
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
                            () -> assertEquals(4L, json.get(0).path("id").asLong()),
                            () -> assertEquals(5L, json.get(1).path("id").asLong()),
                            () -> assertEquals("Jeans Lee", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Jeans Lee", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.get(0).path("imageUrl").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-blue.jpg", json.get(1).path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(3)
    void testGetByColorAttributeId() {
        client.get()
                .uri("/product-gallery/color-attribute/3")
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
                            () -> assertEquals(3, json.size()),
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals(4L, json.get(1).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Jeans Lee", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/iphone15-black.jpg", json.get(0).path("imageUrl").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.get(1).path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetByProductIdAndColorAttributeId() {
        client.get()
                .uri("/product-gallery/product/4/color-attribute/3")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(4L, json.path("id").asLong()),
                            () -> assertEquals("Jeans Lee", json.path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
