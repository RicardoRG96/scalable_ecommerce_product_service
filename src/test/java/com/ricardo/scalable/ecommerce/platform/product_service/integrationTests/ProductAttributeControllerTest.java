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
public class ProductAttributeControllerTest {

    @Autowired
    private Environment env;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetByProductAttributeId() {
        client.get()
                .uri("/product-attribute/1")
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
                            () -> assertEquals("color", json.path("type").asText()),
                            () -> assertEquals("red", json.path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetByProductAttributeIdNotFound() {
        String notExistingId = "100";

        client.get()
                .uri("/product-attribute/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByProductAttributeIdBadRequest() {
        String notANumberParameter = "@";

        client.get()
                .uri("/product-attribute/" + notANumberParameter)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(4)
    void testGetByType() {
        client.get()
                .uri("/product-attribute/type/color")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals(2L, json.get(1).path("id").asLong()),
                            () -> assertEquals(3L, json.get(2).path("id").asLong()),
                            () -> assertEquals("color", json.get(0).path("type").asText()),
                            () -> assertEquals("color", json.get(1).path("type").asText()),
                            () -> assertEquals("color", json.get(2).path("type").asText()),
                            () -> assertEquals("red", json.get(0).path("value").asText()),
                            () -> assertEquals("blue", json.get(1).path("value").asText()),
                            () -> assertEquals("black", json.get(2).path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(5)
    void testGetByTypeNotFound() {
        String notExistingType = "not-existing-type";

        client.get()
                .uri("/product-attribute/type/" + notExistingType)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(6)
    void testGetByValue() {
        client.get()
                .uri("/product-attribute/value/black")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(3L, json.path("id").asLong()),
                            () -> assertEquals("color", json.path("type").asText()),
                            () -> assertEquals("black", json.path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(7)
    void testGetByValueNotFound() {
        String notExistingValue = "not-existing-value";

        client.get()
                .uri("/product-attribute/value/" + notExistingValue)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(8)
    void testGetAllProductAttributes() {
        client.get()
                .uri("/product-attribute")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(7, json.size()),
                            () -> assertTrue(json.isArray()),
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals(2L, json.get(1).path("id").asLong()),
                            () -> assertEquals(3L, json.get(2).path("id").asLong()),
                            () -> assertEquals("color", json.get(0).path("type").asText()),
                            () -> assertEquals("color", json.get(1).path("type").asText()),
                            () -> assertEquals("color", json.get(2).path("type").asText()),
                            () -> assertEquals("red", json.get(0).path("value").asText()),
                            () -> assertEquals("blue", json.get(1).path("value").asText()),
                            () -> assertEquals("black", json.get(2).path("value").asText()),
                            () -> assertEquals("size", json.get(3).path("type").asText()),
                            () -> assertEquals("size", json.get(4).path("type").asText()),
                            () -> assertEquals("size", json.get(5).path("type").asText()),
                            () -> assertEquals("S", json.get(3).path("value").asText()),
                            () -> assertEquals("M", json.get(4).path("value").asText()),
                            () -> assertEquals("L", json.get(5).path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
