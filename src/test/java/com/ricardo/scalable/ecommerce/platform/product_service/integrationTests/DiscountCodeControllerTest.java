package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountCodeControllerTestData.*;

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
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DiscountCodeControllerTest {

    @Autowired
    private WebTestClient client;

    @Autowired
    private Environment env;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetDiscountCodeById() {
        client.get()
                .uri("/discount-code/1")
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
                            () -> assertEquals("10OFFMARCH2025", json.path("code").asText()),
                            () -> assertEquals(4L, json.path("discount").path("id").asLong()),
                            () -> assertEquals(100, json.path("usageLimit").asInt()),
                            () -> assertEquals(0, json.path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetDiscountCodeByIdNotFound() {
        client.get()
                .uri("/discount-code/15682")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetDiscountCodeByCode() {
        client.get()
                .uri("/discount-code/code/20OFFAPRIL2025")
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
                            () -> assertEquals("20OFFAPRIL2025", json.path("code").asText()),
                            () -> assertEquals(3L, json.path("discount").path("id").asLong()),
                            () -> assertEquals(50, json.path("usageLimit").asInt()),
                            () -> assertEquals(0, json.path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetDiscountCodeByCodeNotFound() {
        client.get()
                .uri("/discount-code/code/NOEXISTE2025")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetDiscountCodeByDiscountId() {
        client.get()
                .uri("/discount-code/discount-id/3")
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
                            () -> assertEquals("20OFFAPRIL2025", json.get(0).path("code").asText()),
                            () -> assertEquals(3L, json.get(0).path("discount").path("id").asLong()),
                            () -> assertEquals(50, json.get(0).path("usageLimit").asInt()),
                            () -> assertEquals(0, json.get(0).path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testGetDiscountCodeByDiscountIdNotFound() {
        client.get()
                .uri("/discount-code/discount-id/3521")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetDiscountCodeByCodeAndDiscountId() {
        client.get()
                .uri("/discount-code/code/10DOLLARSOFFMARCH/discount-id/1")
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
                            () -> assertEquals("10DOLLARSOFFMARCH", json.path("code").asText()),
                            () -> assertEquals(1L, json.path("discount").path("id").asLong()),
                            () -> assertEquals(70, json.path("usageLimit").asInt()),
                            () -> assertEquals(0, json.path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    void testGetDiscountCodeByCodeAndDiscountIdNotFound() {
        client.get()
                .uri("/discount-code/code/NOEXISTE2025/discount-id/1")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/discount-code/code/10DOLLARSOFFMARCH/discount-id/1597")
                .exchange()
                .expectStatus().isNotFound();

        client.get()
                .uri("/discount-code/code/NOEXISTE2025/discount-id/1597")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testGetDiscountCodeByUsageLimit() {
        client.get()
                .uri("/discount-code/usage-limit/100")
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
                            () -> assertEquals(4L, json.get(1).path("id").asLong()),
                            () -> assertEquals("10OFFMARCH2025", json.get(0).path("code").asText()),
                            () -> assertEquals("FREESHIPPINGAPRIL2025", json.get(1).path("code").asText()),
                            () -> assertEquals(4L, json.get(0).path("discount").path("id").asLong()),
                            () -> assertEquals(2L, json.get(1).path("discount").path("id").asLong()),
                            () -> assertEquals(100, json.get(0).path("usageLimit").asInt()),
                            () -> assertEquals(100, json.get(1).path("usageLimit").asInt()),
                            () -> assertEquals(0, json.get(0).path("usedCount").asInt()),
                            () -> assertEquals(0, json.get(1).path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(10)
    void testGetDiscountCodeByUsageLimitNotFound() {
        client.get()
                .uri("/discount-code/usage-limit/12974")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testGetDiscountCodeByUsedCount() {
        client.get()
                .uri("/discount-code/used-count/0")
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
                            () -> assertEquals(4, json.size()),
                            () -> assertEquals(1L, json.get(0).path("id").asLong()),
                            () -> assertEquals(2L, json.get(1).path("id").asLong()),
                            () -> assertEquals(3L, json.get(2).path("id").asLong()),
                            () -> assertEquals(4L, json.get(3).path("id").asLong()),
                            () -> assertEquals(4L, json.get(0).path("discount").path("id").asLong()),
                            () -> assertEquals(3L, json.get(1).path("discount").path("id").asLong()),
                            () -> assertEquals(1L, json.get(2).path("discount").path("id").asLong()),
                            () -> assertEquals(2L, json.get(3).path("discount").path("id").asLong())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(12)
    void testGetDiscountCodeByUsedCountNotFound() {
        client.get()
                .uri("/discount-code/used-count/356")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testGetAllDiscountCodes() {
        client.get()
                .uri("/discount-code")
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
    @Order(14)
    void testCreateDiscountCode() {
        DiscountCodeDto requestBody = createDiscountCodeDto();

        client.post()
                .uri("/discount-code")
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
                            () -> assertEquals("20OFFLOYALCUSTOMER", json.path("code").asText()),
                            () -> assertEquals(3L, json.path("discount").path("id").asLong()),
                            () -> assertEquals(100, json.path("usageLimit").asInt()),
                            () -> assertEquals(0, json.path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });        
    }

    @Test
    @Order(15)
    void testCreateDiscountCodeWithNoCode() {
        DiscountCodeDto requestBodyWithNoCode = createDiscountCodeDto();
        requestBodyWithNoCode.setCode("");

        client.post()
                .uri("/discount-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoCode)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(16)
    void testCreateDiscountCodeWithNoDiscountId() {
        DiscountCodeDto requestBodyWithNoDiscountId = createDiscountCodeDto();
        requestBodyWithNoDiscountId.setDiscountId(null);

        client.post()
                .uri("/discount-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoDiscountId)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(17)
    void testCreateDiscountCodeWithNoUsageLimit() {
        DiscountCodeDto requestBodyWithNoUsageLimit = createDiscountCodeDto();
        requestBodyWithNoUsageLimit.setUsageLimit(null);

        client.post()
                .uri("/discount-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoUsageLimit)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(18)
    void testCreateDiscountCodeNotFound() {
        DiscountCodeDto requestBody = createDiscountCodeDto();
        requestBody.setDiscountId(15142L);

        client.post()
                .uri("/discount-code")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(19)
    void testUpdateDiscountCode() {
        DiscountCodeDto requestBody = createDiscountCodeToUpdate();

        client.put()
                .uri("/discount-code/5")
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
                            () -> assertEquals("20OFF-LOYAL-CUSTOMER-APRIL", json.path("code").asText()),
                            () -> assertEquals(3L, json.path("discount").path("id").asLong()),
                            () -> assertEquals(120, json.path("usageLimit").asInt()),
                            () -> assertEquals(0, json.path("usedCount").asInt())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });      
    }

    @Test
    @Order(20)
    void testUpdateDiscountCodeWithNoCode() {
        DiscountCodeDto requestBodyWithNoCode = createDiscountCodeToUpdate();
        requestBodyWithNoCode.setCode("");

        client.put()
                .uri("/discount-code/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoCode)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(21)
    void testUpdateDiscountCodeWithNoDiscountId() {
        DiscountCodeDto requestBodyWithNoDiscountId = createDiscountCodeToUpdate();
        requestBodyWithNoDiscountId.setDiscountId(null);

        client.put()
                .uri("/discount-code/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoDiscountId)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(22)
    void testUpdateDiscountCodeWithNoUsageLimit() {
        DiscountCodeDto requestBodyWithNoUsageLimit = createDiscountCodeToUpdate();
        requestBodyWithNoUsageLimit.setUsageLimit(null);

        client.put()
                .uri("/discount-code/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBodyWithNoUsageLimit)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(23)
    void testUpdateDiscountCodeNotFoundDiscountId() {
        DiscountCodeDto requestBody = createDiscountCodeToUpdate();
        requestBody.setDiscountId(1254L);

        client.put()
                .uri("/discount-code/5")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(24)
    void testUpdateDiscountCodeNotFoundDiscountCodeId() {
        DiscountCodeDto requestBody = createDiscountCodeToUpdate();

        client.put()
                .uri("/discount-code/4415")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
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
