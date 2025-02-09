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
public class ProductSkuControllerTest {

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
    void testGetByProductSkuId() {
        client.get()
                .uri("/product-sku/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.get("id").asLong()),
                            () -> assertEquals("SKU2210", json.get("sku").asText()),
                            () -> assertEquals(1L, json.get("product").path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get("colorAttribute").path("value").asText()),
                            () -> assertEquals(1500.99, json.get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

    }

    @Test
    @Order(2)
    void testGetByProductSkuIdNotFound() {
        String notExistingId = "100";

        client.get()
                .uri("/product-sku/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByProductId() {
        client.get()
                .uri("/product-sku/product/5")
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
                            () -> assertEquals(5, json.size()),
                            () -> assertEquals(5L, json.get(0).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(0).get("product").path("name").asText()),
                            () -> assertEquals("S", json.get(0).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("red", json.get(0).get("colorAttribute").path("value").asText()),
                            () -> assertEquals("M", json.get(1).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("blue", json.get(1).get("colorAttribute").path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetByProductIdNotFound() {
        String notExistingId = "100";

        client.get()
                .uri("/product-sku/product/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetBySku() {
        client.get()
                .uri("/product-sku/sku/SKU2210")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.get("id").asLong()),
                            () -> assertEquals("SKU2210", json.get("sku").asText()),
                            () -> assertEquals(1L, json.get("product").path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get("colorAttribute").path("value").asText()),
                            () -> assertEquals(1500.99, json.get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testGetBySkuNotFound() {
        String notExistingSku = "SKU100";

        client.get()
                .uri("/product-sku/sku/" + notExistingSku)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetBySkuAndIsActive() {
        client.get()
                .uri("/product-sku/sku/SKU2210/isActive/true")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1, json.get("id").asLong()),
                            () -> assertEquals("SKU2210", json.get("sku").asText()),
                            () -> assertEquals(1L, json.get("product").path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get("colorAttribute").path("value").asText()),
                            () -> assertEquals(1500.99, json.get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    void testGetBySkuAndIsActiveNotFound() {
        String notExistingSku = "SKU100";

        client.get()
                .uri("/product-sku/sku/" + notExistingSku + "/isActive/true")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testGetBySizeAttributeId() {
        client.get()
                .uri("/product-sku/sizeAttributeId/8")
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
                            () -> assertEquals(1L, json.get(0).get("product").path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get(0).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(0).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(1500.99, json.get(0).get("price").asDouble()),
                            () -> assertEquals(2L, json.get(1).get("product").path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(1).get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get(1).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(1).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(999.99, json.get(1).get("price").asDouble()),
                            () -> assertEquals(3L, json.get(2).get("product").path("id").asLong()),
                            () -> assertEquals("Balon premier league 2025", json.get(2).get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get(2).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("blue", json.get(2).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(29.99, json.get(2).get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(10)
    void testGetBySizeAttributeIdNotFound() {
        String notExistingId = "100";

        client.get()
                .uri("/product-sku/sizeAttributeId/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testGetByColorAttributeId() {
        client.get()
                .uri("/product-sku/colorAttributeId/3")
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
                            () -> assertEquals(5, json.size()),
                            () -> assertEquals(1L, json.get(0).get("product").path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get(0).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(0).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(1500.99, json.get(0).get("price").asDouble()),
                            () -> assertEquals(2L, json.get(1).get("product").path("id").asLong()),
                            () -> assertEquals("Asus Zenbook", json.get(1).get("product").path("name").asText()),
                            () -> assertEquals("none-size", json.get(1).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(1).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(999.99, json.get(1).get("price").asDouble()),
                            () -> assertEquals(4L, json.get(2).get("product").path("id").asLong()),
                            () -> assertEquals("Jeans Lee", json.get(2).get("product").path("name").asText()),
                            () -> assertEquals("L", json.get(2).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(2).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(29.99, json.get(2).get("price").asDouble()),
                            () -> assertEquals(5L, json.get(3).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(3).get("product").path("name").asText()),
                            () -> assertEquals("L", json.get(3).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(3).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.get(3).get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testGetByColorAttributeIdNotFound() {
        String notExistingId = "100";

        client.get()
                .uri("/product-sku/colorAttributeId/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testGetByProductIdAndSizeAttributeId() {
        client.get()
                .uri("/product-sku/productId/5/sizeAttributeId/6")
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
                            () -> assertEquals(5L, json.get(0).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(0).get("product").path("name").asText()),
                            () -> assertEquals("L", json.get(0).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(0).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.get(0).get("price").asDouble()),
                            () -> assertEquals(5L, json.get(1).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(1).get("product").path("name").asText()),
                            () -> assertEquals("L", json.get(1).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("red", json.get(1).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.get(1).get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(14)
    void testGetByProductIdAndSizeAttributeIdNotFound() {
        String notExistingSizeId = "100";

        client.get()
                .uri("/product-sku/productId/5/sizeAttributeId/" + notExistingSizeId)
                .exchange()
                .expectStatus().isNotFound();

        String notExistingProductId = "100";

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/6")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/" + notExistingSizeId)
                .exchange()
                .expectStatus().isNotFound(); 
    }

    @Test
    @Order(15)
    void testGetByProductIdAndColorAttributeId() {
        client.get()
                .uri("/product-sku/productId/5/colorAttributeId/3")
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
                            () -> assertEquals(5L, json.get(0).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(0).get("product").path("name").asText()),
                            () -> assertEquals("L", json.get(0).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(0).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.get(0).get("price").asDouble()),
                            () -> assertEquals(5L, json.get(1).get("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.get(1).get("product").path("name").asText()),
                            () -> assertEquals("M", json.get(1).get("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.get(1).get("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.get(1).get("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(16)
    void testGetByProductIdAndColorAttributeIdNotFound() {
        String notExistingColorId = "100";

        client.get()
                .uri("/product-sku/productId/5/colorAttributeId/" + notExistingColorId)
                .exchange()
                .expectStatus().isNotFound();

        String notExistingProductId = "100";

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/colorAttributeId/3")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/colorAttributeId/" + notExistingColorId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(17)
    void testGetByProductIdAndSizeAttributeIdAndColorAttributeId() {
        client.get()
                .uri("/product-sku/productId/5/sizeAttributeId/6/colorAttributeId/3")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertFalse(json.isArray()),
                            () -> assertEquals(5L, json.path("product").path("id").asLong()),
                            () -> assertEquals("Polera Puma", json.path("product").path("name").asText()),
                            () -> assertEquals("L", json.path("sizeAttribute").path("value").asText()),
                            () -> assertEquals("black", json.path("colorAttribute").path("value").asText()),
                            () -> assertEquals(19.99, json.path("price").asDouble())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(18)
    void testGetByProductIdAndSizeAttributeIdAndColorAttributeIdNotFound() {
        String notExistingProductId = "100";
        String notExistingSizeId = "100";
        String notExistingColorId = "100";

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/6/colorAttributeId/3")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/5/sizeAttributeId/" + notExistingSizeId + "/colorAttributeId/3")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/5/sizeAttributeId/6/colorAttributeId/" + notExistingColorId)
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/" + notExistingSizeId + "/colorAttributeId/3")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/6/colorAttributeId/" + notExistingColorId)
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/product-sku/productId/" + notExistingProductId + "/sizeAttributeId/" + notExistingSizeId + "/colorAttributeId/" + notExistingColorId)
                .exchange()
                .expectStatus().isNotFound();
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
