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
public class DiscountControllerTest {

    @Autowired
    private Environment env;

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetDiscountById() {
        client.get()
                .uri("/discounts/1")
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
                            () -> assertEquals("fixed_amount", json.path("discountType").asText()),
                            () -> assertEquals(10.00, json.path("discountValue").asDouble()),
                            () -> assertEquals("2025-03-01T00:00:00.000-03:00", json.path("startDate").asText()),
                            () -> assertEquals("2025-03-31T23:59:59.000-03:00", json.path("endDate").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetDiscountByProductSkuId() {
        client.get()
                .uri("/discounts/product_sku/1")
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
                            () -> assertEquals("fixed_amount", json.get(0).path("discountType").asText()),
                            () -> assertEquals(10.00, json.get(0).path("discountValue").asDouble()),
                            () -> assertEquals("2025-03-01T00:00:00.000-03:00", json.get(0).path("startDate").asText()),
                            () -> assertEquals("2025-03-31T23:59:59.000-03:00", json.get(0).path("endDate").asText()),
                            () -> assertEquals(4L, json.get(1).path("id").asLong()),
                            () -> assertEquals("percentage", json.get(1).path("discountType").asText()),
                            () -> assertEquals(10.00, json.get(1).path("discountValue").asDouble()),
                            () -> assertEquals("2025-02-01T00:00:00.000-03:00", json.get(1).path("startDate").asText()),
                            () -> assertEquals("2025-02-28T23:59:59.000-03:00", json.get(1).path("endDate").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    void testProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertArrayEquals(new String[] { "test" }, activeProfiles);
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public;NON_KEYWORDS=value", env.getProperty("spring.datasource.url"));
    }

}
