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
                            () -> assertNotNull(json),
                            () -> assertEquals(product.getId(), json.path("id").asLong()),
                            () -> assertEquals(product.getName(), json.path("name").asText()),
                            () -> assertEquals(product.getDescription(), json.path("description").asText()),
                            () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
                            () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
                            () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
                            () -> assertEquals(product.getIsActive(), json.path("isActive").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        String notExistingProductId = "50";

        client.get()
                .uri("/" + notExistingProductId)
                .exchange()
                .expectStatus().isNotFound();
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

    private Product createProduct002() {
        Product product = new Product();
        Category category = new Category(
            2L, 
            "Notebooks", 
            "Computadores portatiles", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );
        Brand brand = new Brand(
            1L, 
            "ASUS", 
            "Empresa multinacional de tecnología", 
            "https://example.com/asus_logo.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        product.setId(2L);
        product.setSku("SKU2501");
        product.setUpc("UPC1515");
        product.setName("Asus Zenbook");
        product.setDescription("Notebook de ultima generacion");
        product.setCategory(category);
        product.setBrand(brand);
        product.setPrice(999.99);
        product.setStock(200);
        product.setImageUrl("https://example.com/images/asus_zenbook.jpg");
        product.setIsActive(true);
        product.setIsFeatured(false);
        product.setIsOnSale(true);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return product;
    }

    @Test
    @Order(2)
    void testGetByName() {
        Product product = createProduct001();

        client.get()
                .uri("/product-name/iPhone 15")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(product.getSku(), json.path("sku").asText()),
                            () -> assertEquals(product.getName(), json.path("name").asText()),
                            () -> assertEquals(product.getDescription(), json.path("description").asText()),
                            () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
                            () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
                            () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
                            () -> assertEquals(product.getIsFeatured(), json.path("isFeatured").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        String notExistingProductName = "Samsung Galaxy s22";

        client.get()
                .uri("/product-name/" + notExistingProductName)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetBySku() {
        Product product = createProduct001();

        client.get()
                .uri("/product-sku/SKU2210")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(product.getSku(), json.path("sku").asText()),
                            () -> assertEquals(product.getName(), json.path("name").asText()),
                            () -> assertEquals(product.getDescription(), json.path("description").asText()),
                            () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
                            () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
                            () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
                            () -> assertEquals(product.getStock(), json.path("stock").asInt()),
                            () -> assertEquals(product.getIsOnSale(), json.path("isOnSale").asBoolean())

                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        String notExistingSku = "12345646";

        client.get()
                .uri("/product-sku/" + notExistingSku)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testGetAllProducts() {
        Product product1 = createProduct001();
        Product product2 = createProduct002();

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
                            () -> assertEquals(3, json.size()),
                            () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
                            () -> assertEquals(product2.getSku(), json.get(1).path("sku").asText()),
                            () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
                            () -> assertEquals(product2.getName(), json.get(1).path("name").asText()),
                            () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
                            () -> assertEquals(product2.getDescription(), json.get(1).path("description").asText()),
                            () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
                            () -> assertEquals(product2.getCategory().getName(), json.get(1).path("category").path("name").asText()),
                            () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
                            () -> assertEquals(product2.getBrand().getName(), json.get(1).path("brand").path("name").asText()),
                            () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble()),
                            () -> assertEquals(product2.getPrice(), json.get(1).path("price").asDouble()),
                            () -> assertEquals(true, json.get(0).path("isFeatured").asBoolean()),
                            () -> assertEquals(false, json.get(1).path("isFeatured").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(5)
    void testCreateProduct() {
        ProductCreationDto productCreationRequest = new ProductCreationDto();
        productCreationRequest.setSku("pepe1321");
        productCreationRequest.setUpc("upcexample");
        productCreationRequest.setName("Audifonos Huawei");
        productCreationRequest.setDescription("description example");
        productCreationRequest.setCategoryId(1L);
        productCreationRequest.setBrandId(2L);
        productCreationRequest.setPrice(70.99); 
        productCreationRequest.setStock(25);
        productCreationRequest.setImageUrl("/logos/logo-huawei.png");
        productCreationRequest.setIsActive(true);
        productCreationRequest.setIsFeatured(true);
        productCreationRequest.setIsOnSale(false);

        client.post()
                .uri("/")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productCreationRequest)
                .exchange()
                .expectStatus().is5xxServerError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertEquals("ññañ", json.path("message").asText()),
                            () -> assertNull(json),
                            () -> assertEquals("sku", json.path("sku").asText()),
                            () -> assertEquals("upc", json.path("upc").asText()),
                            () -> assertEquals("name", json.path("name").asText()),
                            () -> assertEquals("categoryName", json.path("category").path("name").asText()),
                            () -> assertEquals("brandName", json.path("brand").path("name").asText()),
                            () -> assertEquals(false, json.path("isActive").asBoolean()),
                            () -> assertEquals(false, json.path("isFeatured").asBoolean()),
                            () -> assertEquals(true, json.path("isOnSale").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testUpdateProduct() {
        Product productUpdateRequest = createProduct001();
        productUpdateRequest.setName("iPhone 16");
        productUpdateRequest.setPrice(2000.00);

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
                            () -> assertEquals(productUpdateRequest.getSku(), json.path("sku").asText()),
                            () -> assertEquals(productUpdateRequest.getUpc(), json.path("upc").asText()),
                            () -> assertEquals(productUpdateRequest.getName(), json.path("name").asText()),
                            () -> assertEquals(productUpdateRequest.getCategory().getName(), json.path("category").path("name").asText()),
                            () -> assertEquals(productUpdateRequest.getBrand().getName(), json.path("brand").path("name").asText()),
                            () -> assertEquals(productUpdateRequest.getIsActive(), json.path("isActive").asBoolean()),
                            () -> assertEquals(productUpdateRequest.getIsFeatured(), json.path("isFeatured").asBoolean()),
                            () -> assertEquals(productUpdateRequest.getIsOnSale(), json.path("isOnSale").asBoolean())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
    }

    @Test
    @Order(7)
    void testDeleteProduct() {
        client.delete()
                .uri("/3")
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
                            () -> assertEquals(2, json.size()),
                            () -> assertTrue(json.isArray())
                        );
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

        client.get()
                .uri("/3")
                .exchange()
                .expectStatus().isNotFound();
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
