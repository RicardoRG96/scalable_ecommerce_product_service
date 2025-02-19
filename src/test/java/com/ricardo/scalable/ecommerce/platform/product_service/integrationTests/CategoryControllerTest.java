package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

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
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.CategoryControllerTestData.*;

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
                            () -> assertEquals(1L, json.path("id").asLong()),
                            () -> assertEquals("Hombre", json.path("name").asText()),
                            () -> assertEquals("Descripcion hombre", json.path("description").asText()),
                            () -> assertEquals("null", json.path("parent").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetByIdNotFound() {
        String notExistingCategoryId = "50";

        client.get()
                .uri("/" + notExistingCategoryId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByName() {
        client.get()
                .uri("/categories/name/Smartphone")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(6L, json.path("id").asLong()),
                            () -> assertEquals("Smartphone", json.path("name").asText()),
                            () -> assertEquals("Telefonos celulares", json.path("description").asText()),
                            () -> assertEquals("Tecnologia", json.path("parent").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

    }

    @Test
    @Order(4)
    void testGetByNameNotFound() {
        String notExistingCategoryName = "PlayStation 5";

        client.get()
                .uri("/categories/name/" + notExistingCategoryName)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetAllCategories() {
        client.get()
            .uri("/categories")
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
                        () -> assertEquals(9, json.size()),
                        () -> assertEquals(1L, json.get(0).path("id").asLong()),
                        () -> assertEquals(2L, json.get(1).path("id").asLong()),
                        () -> assertEquals("Hombre", json.get(0).path("name").asText()),
                        () -> assertEquals("Tecnologia", json.get(1).path("name").asText()),
                        () -> assertEquals("Descripcion hombre", json.get(0).path("description").asText()),
                        () -> assertEquals("Descripcion tecnologia", json.get(1).path("description").asText()),
                        () -> assertEquals("null", json.get(0).path("parent").asText()),
                        () -> assertEquals("null", json.get(1).path("parent").asText())
                    );
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
    }

    @Test
    @Order(6)
    void testCreateCategory() {
        CategoryCreationDto categoryCreationRequest = new CategoryCreationDto();
        categoryCreationRequest.setName("Running");
        categoryCreationRequest.setDescription("Descripcion Running");
        categoryCreationRequest.setParentId(3L);

        client.post()
                .uri("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(categoryCreationRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(10L, json.path("id").asLong()),
                            () -> assertEquals(categoryCreationRequest.getName(), json.path("name").asText()),
                            () -> assertEquals(categoryCreationRequest.getDescription(), json.path("description").asText()),
                            () -> assertEquals("Deportes", json.path("parent").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(7)
    void testCreateCategoryBadRequest() {
        CategoryCreationDto categoryCreationBadRequest = new CategoryCreationDto();

        client.post()
                .uri("/categories")
                .bodyValue(categoryCreationBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(8)
    void testUpdateCategory() {
        Category category = createCategory002();
        category.setName("Tecno");
        category.setDescription("Descripcion tecno");
        category.setParent(null);

        client.put()
                .uri("/categories/2")
                .bodyValue(category)
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
                            () -> assertEquals(category.getName(), json.path("name").asText()),
                            () -> assertEquals(category.getDescription(), json.path("description").asText()),
                            () -> assertEquals("null", json.path("parent").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(9)
    void testBadRequestUpdateCategory() {
        Category categoryBadRequest = new Category();

        client.put()
                .uri("/categories/2")
                .bodyValue(categoryBadRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(10)
    void testUpdateCategoryNotFound() {
        Category category = createCategory002();

        String notExistingCategoryId = "50";

        client.put()
                .uri("/categories/" + notExistingCategoryId)
                .bodyValue(category)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(11)
    void testDeleteCategory() {
        client.delete()
                .uri("/categories/10")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/categories")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(9, json.size()),
                            () -> assertTrue(json.isArray())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }
    
    @Test
    @Order(12)
    void testGetDeletedCategory() {
        client.get()
                .uri("/categories/10")
                .exchange()
                .expectStatus().isNotFound();
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
