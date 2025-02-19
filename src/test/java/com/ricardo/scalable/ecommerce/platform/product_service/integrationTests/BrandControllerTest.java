package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

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
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.BrandControllerTestData.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class BrandControllerTest {

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
    void testGetById() {
        client.get()
            .uri("/brands/1")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(1L, json.get("id").asLong()),
                        () -> assertEquals("Lee", json.get("name").asText()),
                        () -> assertEquals("Marca líder en moda", json.get("description").asText()),
                        () -> assertEquals("https://example.com/lee_logo.png", json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(2)
    void testGetByIdNotFound() {
        String notExistingBrandId = "999";

        client.get()
            .uri("/brands/" + notExistingBrandId)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByName() {
        client.get()
            .uri("/brands/name/Apple")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(2L, json.get("id").asLong()),
                        () -> assertEquals("Apple", json.get("name").asText()),
                        () -> assertEquals("Marca líder en tecnologia", json.get("description").asText()),
                        () -> assertEquals("https://example.com/apple_logo.png", json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

    }

    @Test
    @Order(4)
    void testGetByByNameNotFound() {
        String notExistingBrandName = "NotExistingBrand";

        client.get()
            .uri("/brands/name/" + notExistingBrandName)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetAllBrands() {
        client.get()
            .uri("/brands")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(6, json.size()),
                        () -> assertEquals(1L, json.get(0).get("id").asLong()),
                        () -> assertEquals("Lee", json.get(0).get("name").asText()),
                        () -> assertEquals("Marca líder en moda", json.get(0).get("description").asText()),
                        () -> assertEquals("https://example.com/lee_logo.png", json.get(0).get("logoUrl").asText()),
                        () -> assertEquals(2L, json.get(1).get("id").asLong()),
                        () -> assertEquals("Apple", json.get(1).get("name").asText()),
                        () -> assertEquals("Marca líder en tecnologia", json.get(1).get("description").asText()),
                        () -> assertEquals("https://example.com/apple_logo.png", json.get(1).get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(6)
    void testCreateBrand() {
        BrandCreationDto brandCreationRequest = new BrandCreationDto();
        brandCreationRequest.setName("Samsung");
        brandCreationRequest.setDescription("Empresa multinacional de tecnología");
        brandCreationRequest.setLogoUrl("https://example.com/samsung_logo.png");
        
        client.post()
            .uri("/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(brandCreationRequest)
            .exchange()
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(7, json.get("id").asLong()),
                        () -> assertEquals(brandCreationRequest.getName(), json.get("name").asText()),
                        () -> assertEquals(brandCreationRequest.getDescription(), json.get("description").asText()),
                        () -> assertEquals(brandCreationRequest.getLogoUrl(), json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(7)
    void testCreateBrandBadRequest() {
        BrandCreationDto brandCreationBadRequest = new BrandCreationDto();

        client.post()
            .uri("/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(brandCreationBadRequest)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    void testUpdateBrand() {
        Brand brandRequest = createBrand001();
        brandRequest.setName("Lee Inc.");
        brandRequest.setDescription("Empresa líder en moda callejera");

        client.put()
                .uri("/brands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(brandRequest)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertEquals(brandRequest.getId(), json.get("id").asLong()),
                            () -> assertEquals(brandRequest.getName(), json.get("name").asText()),
                            () -> assertEquals(brandRequest.getDescription(), json.get("description").asText()),
                            () -> assertEquals(brandRequest.getLogoUrl(), json.get("logoUrl").asText())
                        );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(9)
    void testUpdateBrandNotFound() {
        Brand brandRequest = createBrand001();
        String notExistingBrandId = "999";

        client.put()
            .uri("/brands/" + notExistingBrandId)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(brandRequest)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    void testUpdateBrandBadRequest() {
        Brand brandBadRequest = new Brand();

        client.put()
            .uri("/brands/1")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(brandBadRequest)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    void testDeleteBrand() {
        client.delete()
            .uri("/brands/7")
            .exchange()
            .expectStatus().isNoContent();

        client.get()
            .uri("/brands")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(6, json.size()),
                        () -> assertTrue(json.isArray())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(12)
    void testGetDeletedBrand() {
        client.get()
            .uri("/brands/7")
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
