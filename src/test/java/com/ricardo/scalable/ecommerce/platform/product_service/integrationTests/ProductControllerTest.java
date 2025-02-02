package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.*;
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
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductControllerTest {

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
    void testGetByProductId() {
        client.get()
                .uri("/1")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(response -> {
                    try {
                        JsonNode json = objectMapper.readTree(response.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(1L, json.path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.path("description").asText()),
                            () -> assertEquals("Smartphone", json.path("category").path("name").asText()),
                            () -> assertEquals("Apple", json.path("brand").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetNotExistingProduct() {
        String notExistingProductId = "50";

        client.get()
                .uri("/" + notExistingProductId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByName() {
        client.get()
                .uri("/name/Jeans Lee")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals("Jeans Lee", json.path("name").asText()),
                            () -> assertEquals("Descripcion pantalones Lee", json.path("description").asText()),
                            () -> assertEquals("Jeans-hombre", json.path("category").path("name").asText()),
                            () -> assertEquals("Lee", json.path("brand").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetByNotExistingProductName() {
        String notExistingProductName = "Samsung Galaxy s22";

        client.get()
                .uri("/name/" + notExistingProductName)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetAllProducts() {
        client.get()
                .uri("/")
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
                            () -> assertEquals("iPhone 15", json.get(0).path("name").asText()),
                            () -> assertEquals("Asus Zenbook", json.get(1).path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.get(0).path("description").asText()),
                            () -> assertEquals("Notebook de ultima generacion", json.get(1).path("description").asText()),
                            () -> assertEquals("Smartphone", json.get(0).path("category").path("name").asText()),
                            () -> assertEquals("Notebooks", json.get(1).path("category").path("name").asText()),
                            () -> assertEquals("Apple", json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals("ASUS", json.get(1).path("brand").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testCreateProduct() {
        ProductCreationDto productCreationRequest = new ProductCreationDto();
        productCreationRequest.setName("Camiseta FC Barcelona");
        productCreationRequest.setDescription("Descripcion camiseta FC Barcelona");
        productCreationRequest.setCategoryId(8L);
        productCreationRequest.setBrandId(4L);
        productCreationRequest.setCover("https://example.com/camiseta_barcelona.png");

        client.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productCreationRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(6, json.path("id").asLong()),
                            () -> assertEquals("Camiseta FC Barcelona", json.path("name").asText()),
                            () -> assertEquals("Descripcion camiseta FC Barcelona", json.path("description").asText()),
                            () -> assertEquals("Nike", json.path("brand").path("name").asText()),
                            () -> assertEquals("Futbol", json.path("category").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(7)
    void testUpdateProduct() {
        Product productUpdateRequest = createProduct001();
        productUpdateRequest.setName("iPhone 15 Pro");

        client.put()
                .uri("/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productUpdateRequest)
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
                            () -> assertEquals("iPhone 15 Pro", json.path("name").asText()),
                            () -> assertEquals("Smartphone Apple", json.path("description").asText()),
                            () -> assertEquals("Smartphone", json.path("category").path("name").asText()),
                            () -> assertEquals("Apple", json.path("brand").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    private Product createProduct001() {
        Product product = new Product();
        Category category = new Category(
            2L, 
            "Tecnologia", 
            "Descripcion tecnologia",
            null,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Category subCategory = new Category(
            6L, 
            "Smartphone", 
            "Telefonos celulares",
            category,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            2L, 
            "Apple", 
            "Marca líder en tecnologia", 
            "https://example.com/apple_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(1L);
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setCategory(subCategory);
        product.setBrand(brand);
        product.setCover("https://example.com/images/iphone15.jpg");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

    @Test
    @Order(8)
    void testDeleteProduct() {
        client.delete()
                .uri("/6")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(5, json.size()),
                            () -> assertTrue(json.isArray())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        client.get()
                .uri("/6")
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
