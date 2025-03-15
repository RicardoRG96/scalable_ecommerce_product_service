package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountControllerTestData.*;

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
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

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
    void testGetDiscountByIdNotFound() {
        client.get()
                .uri("/discounts/1000")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
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
    @Order(4)
    void testGetDiscountByProductSkuIdNotFound() {
        client.get()
                .uri("/discounts/product_sku/10000")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetDiscountByType() {
        client.get()
                .uri("/discounts/discount_type/percentage")
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
                            () -> assertEquals(3L, json.get(0).path("id").asLong()),
                            () -> assertEquals("percentage", json.get(0).path("discountType").asText()),
                            () -> assertEquals(20.00, json.get(0).path("discountValue").asDouble()),
                            () -> assertEquals("2025-03-03T00:00:00.000-03:00", json.get(0).path("startDate").asText()),
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
    @Order(6)
    void testGetDiscountByTypeNotFound() {
        client.get()
                .uri("/discounts/discount_type/promo")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void getDiscountByValue() {
        client.get()
                .uri("/discounts/discount_value/10.00")
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
    @Order(8)
    void testGetDiscountByValueNotFound() {
        client.get()
                .uri("/discounts/discount_value/999999.00")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testVerifyValidityPeriod() {
        client.get()
                .uri("/discounts/validity/1")
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
    @Order(10)
    void testVerifyValidityPeriodNotValid() {
        client.get()
                .uri("/discounts/validity/4")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testCheckOverlappingDiscount() {
        client.get()
                .uri("/discounts/overlapping/1/2025-03-01T00:00:00.000000/2025-03-31T23:59:59.000000")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(2, json.path("overlappingDiscounts").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testCheckOverlappingDiscountWithNotExistingProductSkuId() {
        client.get()
                .uri("/discounts/overlapping/100/2025-03-01T00:00:00.000000/2025-03-31T23:59:59.000000")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(0, json.path("overlappingDiscounts").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(13)
    void testCheckOverlappingDiscountWithNotOverlapDates() {
        client.get()
        .uri("/discounts/overlapping/1/2025-02-01T00:00:00.000000/2025-02-28T23:59:59.000000")
        .exchange()
        .expectStatus().isOk()
        .expectHeader().contentType(MediaType.APPLICATION_JSON)
        .expectBody()
        .consumeWith(res -> {
            try {
                JsonNode json = objectMapper.readTree(res.getResponseBody());
                assertAll(
                    () -> assertNotNull(json),
                    () -> assertEquals(1, json.path("overlappingDiscounts").asInt())
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    @Order(14)
    void testCheckOverlappingDiscountWithInvalidDates() {
        client.get()
        .uri("/discounts/overlapping/1/2025-03-01T00:00:00.000-03:00/2025-03-31T23:59:59.000-03:00")
        .exchange()
        .expectStatus().isBadRequest();
    }

    @Test
    @Order(15)
    void testGetAllDiscounts() {
        client.get()
                .uri("/discounts")
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
                            () -> assertEquals(4, json.size())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(16)
    void testCreateDiscount() {
        DiscountDto requestBody = createDiscountDto();

        client.post()
                .uri("/discounts")
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
                            () -> assertEquals(5L, json.path("id").asLong()),
                            () -> assertEquals("fixed_amount", json.path("discountType").asText()),
                            () -> assertEquals(8.00, json.path("discountValue").asDouble()),
                            () -> assertEquals("2025-03-14T00:00:00.000-03:00", json.path("startDate").asText()),
                            () -> assertEquals("2025-03-21T23:59:59.000-03:00", json.path("endDate").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(17)
    void testCreateDiscountBadRequest() {
        DiscountDto requestBody = new DiscountDto();

        client.post()
                .uri("/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();        
    }

    @Test
    @Order(18)
    void testCreateDiscountNotFound() {
        DiscountDto requestBody = createDiscountDto();
        requestBody.setProductSkuIds(List.of(100L));

        client.post()
                .uri("/discounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(19)
    void testUpdateDiscount() {
        DiscountDto requestBody = createDiscountToUpdate();

        client.put()
                .uri("/discounts/5")
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
                            () -> assertEquals(5L, json.path("id").asLong()),
                            () -> assertEquals("fixed_amount", json.path("discountType").asText()),
                            () -> assertEquals(9.00, json.path("discountValue").asDouble()),
                            () -> assertEquals("2025-03-14T00:00:00.000-03:00", json.path("startDate").asText()),
                            () -> assertEquals("2025-03-22T23:59:59.000-03:00", json.path("endDate").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(20)
    void testUpdateDiscountBadRequest() {
        DiscountDto requestBody = new DiscountDto();

        client.put()
                .uri("/discounts/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(21)
    void testUpdateDiscountNotFound() {
        DiscountDto requestBody = createDiscountToUpdate();

        client.put()
                .uri("/discounts/500")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();

        DiscountDto requestBodyWithNotExistingProductSkuIds = createDiscountToUpdate();
        requestBodyWithNotExistingProductSkuIds.setProductSkuIds(List.of(5000L, 6000L));

        client.put()
        .uri("/discounts/5")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(requestBodyWithNotExistingProductSkuIds)
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
