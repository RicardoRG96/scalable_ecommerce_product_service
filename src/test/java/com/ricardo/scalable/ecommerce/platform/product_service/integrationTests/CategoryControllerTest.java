package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CategoryControllerTest {

    @Autowired
    private Environment env;

    private ObjectMapper objectMapper;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetById() {
        Category category = createCategory001();

        client.get()
                .uri("/categories/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(category.getId(), json.path("id").asLong()),
                            () -> assertEquals(category.getName(), json.path("name").asText()),
                            () -> assertEquals(category.getDescription(), json.path("description").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        String notExistingCategoryId = "50";

        client.get()
                .uri("/" + notExistingCategoryId)
                .exchange()
                .expectStatus().isNotFound();

    }

    private Category createCategory001() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Smartphone");
        category.setDescription("Telefonos celulares");
        category.setCreatedAt(Timestamp.from(Instant.now()));
        category.setUpdatedAt(Timestamp.from(Instant.now()));

        return category;
    }

    private Category createCategory002() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Notebooks");
        category.setDescription("Computadores portatiles");
        category.setCreatedAt(Timestamp.from(Instant.now()));
        category.setUpdatedAt(Timestamp.from(Instant.now()));

        return category;
    }

    @Test
    void testProfile() {
        assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
    }

    @Test
    void testApplicationPropertyFile() {
        assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
    }

}
