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
    void testProfile() {
        String[] activeProfiles = env.getActiveProfiles();
        assertArrayEquals(new String[] { "test" }, activeProfiles);
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
    }

}
