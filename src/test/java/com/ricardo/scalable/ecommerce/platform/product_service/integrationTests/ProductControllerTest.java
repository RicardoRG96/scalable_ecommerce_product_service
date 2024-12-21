package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

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
        Product product = createProduct001();

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
                            () -> assertNull(json),
                            () -> assertEquals(product.getId(), json.path("id").asLong()),
                            () -> assertEquals("ñacsn", json.path("name").asText()),
                            () -> assertEquals("descripcion", json.path("description").asText()),
                            () -> assertEquals("kaka", json.path("brand").path("name").asText())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    private Product createProduct001() {
        Product product = new Product();
        Category category = new Category(
            1L, 
            "Smartphone", 
            "Telefonos celulares", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            1L, 
            "Apple", 
            "Marca líder en tecnologia", 
            "https://example.com/apple_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(1L);
        product.setSku("SKU2210");
        product.setUpc("UPC6569");
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(1500.99);
        product.setStock(540);
        product.setImageUrl("https://example.com/images/iphone15_algodon.jpg");
        product.setIsActive(true);
        product.setIsFeatured(true);
        product.setIsOnSale(true);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
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
