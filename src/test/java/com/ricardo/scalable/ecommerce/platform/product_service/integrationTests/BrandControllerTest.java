package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

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
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

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
        Brand brand = createBrand001();

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
                        () -> assertEquals(brand.getId(), json.get("id").asLong()),
                        () -> assertEquals(brand.getName(), json.get("name").asText()),
                        () -> assertEquals(brand.getDescription(), json.get("description").asText()),
                        () -> assertEquals(brand.getLogoUrl(), json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        String notExistingBrandId = "999";

        client.get()
            .uri("/brands/" + notExistingBrandId)
            .exchange()
            .expectStatus().isNotFound();
    }

    private Brand createBrand001() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Apple");
        brand.setDescription("Marca líder en tecnologia");
        brand.setLogoUrl("https://example.com/apple_logo.png");
        brand.setCreatedAt(Timestamp.from(Instant.now()));
        brand.setUpdatedAt(Timestamp.from(Instant.now()));
        return brand;
    }

    private Brand createBrand002() {
        Brand brand = new Brand();
        brand.setId(2L);
        brand.setName("ASUS");
        brand.setDescription("Empresa multinacional de tecnología");
        brand.setLogoUrl("https://example.com/asus_logo.png");
        brand.setCreatedAt(Timestamp.from(Instant.now()));
        brand.setUpdatedAt(Timestamp.from(Instant.now()));
        return brand;
    }

    @Test
    @Order(2)
    void testGetByName() {
        Brand brand = createBrand002();

        client.get()
            .uri("/brands/name/ASUS")
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .consumeWith(res -> {
                try {
                    JsonNode json = objectMapper.readTree(res.getResponseBody());
                    assertAll(
                        () -> assertEquals(brand.getId(), json.get("id").asLong()),
                        () -> assertEquals(brand.getName(), json.get("name").asText()),
                        () -> assertEquals(brand.getDescription(), json.get("description").asText()),
                        () -> assertEquals(brand.getLogoUrl(), json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        String notExistingBrandName = "NotExistingBrand";

        client.get()
            .uri("/brands/name/" + notExistingBrandName)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetAllBrands() {
        Brand brand001 = createBrand001();
        Brand brand002 = createBrand002();

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
                        () -> assertEquals(4, json.size()),
                        () -> assertEquals(brand001.getId(), json.get(0).get("id").asLong()),
                        () -> assertEquals(brand001.getName(), json.get(0).get("name").asText()),
                        () -> assertEquals(brand001.getDescription(), json.get(0).get("description").asText()),
                        () -> assertEquals(brand001.getLogoUrl(), json.get(0).get("logoUrl").asText()),
                        () -> assertEquals(brand002.getId(), json.get(1).get("id").asLong()),
                        () -> assertEquals(brand002.getName(), json.get(1).get("name").asText()),
                        () -> assertEquals(brand002.getDescription(), json.get(1).get("description").asText()),
                        () -> assertEquals(brand002.getLogoUrl(), json.get(1).get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
    }

    @Test
    @Order(4)
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
                        () -> assertEquals(5, json.get("id").asLong()),
                        () -> assertEquals(brandCreationRequest.getName(), json.get("name").asText()),
                        () -> assertEquals(brandCreationRequest.getDescription(), json.get("description").asText()),
                        () -> assertEquals(brandCreationRequest.getLogoUrl(), json.get("logoUrl").asText())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        BrandCreationDto brandCreationBadRequest = new BrandCreationDto();

        client.post()
            .uri("/brands")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(brandCreationBadRequest)
            .exchange()
            .expectStatus().isBadRequest();
    }

    @Test
    @Order(5)
    void testUpdateBrand() {
        Brand brandRequest = createBrand001();
        brandRequest.setName("Apple Inc.");
        brandRequest.setDescription("Empresa líder en tecnología");

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

        Brand brandBadRequest = new Brand();

        client.put()
                .uri("/brands/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(brandBadRequest)
                .exchange()
                .expectStatus().isBadRequest();

        String notExistingBrandId = "999";

        client.put()
                .uri("/brands/" + notExistingBrandId)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(brandRequest)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(6)
    void testDeleteBrand() {
        client.delete()
            .uri("/brands/5")
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
                        () -> assertEquals(4, json.size()),
                        () -> assertTrue(json.isArray())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        client.get()
            .uri("/brands/5")
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
        assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
    }

}
