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
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeControllerTestData.*;

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
    void testGetById() {
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
                            () -> assertEquals(8, json.size()),
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
    @Order(9)
    void testCreateProductAttribute() {
        ProductAttributeCreationDto requestBody = new ProductAttributeCreationDto("color", "green");

        client.post()
                .uri("/product-attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(9L, json.path("id").asLong()),
                            () -> assertEquals("color", json.path("type").asText()),
                            () -> assertEquals("green", json.path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(10)
    void testCreateProductAttributeBadRequest() {
        ProductAttributeCreationDto requestBody = new ProductAttributeCreationDto();

        client.post()
                .uri("/product-attribute")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    void testUpdateProductAttribute() {
        ProductAttribute requestBody = createProductAttribute002();
        requestBody.setValue("sky blue");

        client.put()
                .uri("/product-attribute/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(2L, json.path("id").asLong()),
                            () -> assertEquals("color", json.path("type").asText()),
                            () -> assertEquals("sky blue", json.path("value").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testUpdateProductAttributeNotFound() {
        ProductAttribute requestBody = createProductAttribute002();
        String notExistingId = "100";
        requestBody.setValue("sky blue");

        client.put()
                .uri("/product-attribute/" + notExistingId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testUpdateProductAttributeBadRequest() {
        ProductAttribute requestBody = new ProductAttribute();

        client.put()
                .uri("/product-attribute/2")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    void testDeleteProductAttribute() {
        client.delete()
                .uri("/product-attribute/6")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/product-attribute")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(8, json.size()),
                            () -> assertTrue(json.isArray())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(15)
    void testGetDeletedProductAttribute() {
        client.get()
                .uri("/product-attribute/6")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(16)
    void testDeleteProductAttributeNotFound() {
        String notExistingId = "100";

        client.delete()
                .uri("/product-attribute/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(17)
    void testProductSkuWithDeletedAttributes() {
        client.get()
                .uri("/product-sku")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(6L, json.get(5).path("id").asLong()),
                            () -> assertEquals("SKU8564", json.get(5).path("sku").asText()),
                            () -> assertEquals("none-size", json.get(5).path("sizeAttribute").path("value").asText()),
                            () -> assertEquals(9L, json.get(8).path("id").asLong()),
                            () -> assertEquals("SKU1256", json.get(8).path("sku").asText()),
                            () -> assertEquals("none-size", json.get(8).path("sizeAttribute").path("value").asText())
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
