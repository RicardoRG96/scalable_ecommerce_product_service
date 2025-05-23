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
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductGalleryControllerTestData.*;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class ProductGalleryControllerTest {

    @Autowired
    private Environment env;

    @Autowired
    private WebTestClient client;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void testGetById() {
        client.get()
                .uri("/product-gallery/1")
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
                            () -> assertEquals("iPhone 15", json.path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/iphone15-black.jpg", json.path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(2)
    void testGetByIdNotFound() {
        String notExistingId = "999";

        client.get()
                .uri("/product-gallery/" + notExistingId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(3)
    void testGetByProductId() {
        client.get()
                .uri("/product-gallery/product/4")
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
                            () -> assertEquals(4L, json.get(0).path("id").asLong()),
                            () -> assertEquals(5L, json.get(1).path("id").asLong()),
                            () -> assertEquals("Jeans Lee", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Jeans Lee", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.get(0).path("imageUrl").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-blue.jpg", json.get(1).path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(4)
    void testGetByProductIdNotFound() {
        String notExistingProductId = "999";

        client.get()
                .uri("/product-gallery/product/" + notExistingProductId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(5)
    void testGetByColorAttributeId() {
        client.get()
                .uri("/product-gallery/color-attribute/3")
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
                            () -> assertEquals(4L, json.get(1).path("id").asLong()),
                            () -> assertEquals("iPhone 15", json.get(0).path("product").path("name").asText()),
                            () -> assertEquals("Jeans Lee", json.get(1).path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/iphone15-black.jpg", json.get(0).path("imageUrl").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.get(1).path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(6)
    void testGetByColorAttributeIdNotFound() {
        String notExistingColorAttributeId = "999";

        client.get()
                .uri("/product-gallery/color-attribute/" + notExistingColorAttributeId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testGetByProductIdAndColorAttributeId() {
        client.get()
                .uri("/product-gallery/product/4/color-attribute/3")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(4L, json.path("id").asLong()),
                            () -> assertEquals("Jeans Lee", json.path("product").path("name").asText()),
                            () -> assertEquals("https://example.com/images/jeans-lee-black.jpg", json.path("imageUrl").asText())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(8)
    void testGetByProductIdAndColorAttributeIdNotFound() {
        String notExistingProductId = "999";
        String notExistingColorAttributeId = "999";

        client.get()
                .uri("/product-gallery/product/" + notExistingProductId + "/color-attribute/" + notExistingColorAttributeId)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(9)
    void testGetAll() {
        client.get()
                .uri("/product-gallery")
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
                            () -> assertEquals(7, json.size())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(10)
    void testCreateProductGallery() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormRequest();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(8L, json.path("id").asLong()),
                            () -> assertEquals(5L, json.path("product").path("id").asLong()),
                            () -> assertEquals(1L, json.path("colorAttribute").path("id").asLong()),
                            () -> assertTrue(json.path("imageUrl").asText().contains("https://product-gallery-images-ecommerce-test.s3.us-east-2.amazonaws.com")),
                            () -> assertTrue(json.path("imageUrl").asText().contains("ok-example.jpg"))
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(11)
    void testCreateProductGalleryBadRequestEmptyProduct() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyProductName();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    void testCreateProductGalleryBadRequestEmptyColor() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyColorName();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(13)
    void testCreateProductGalleryBadRequestEmptyImage() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyImage();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(14)
    void testCreateProductGalleryBadRequestImageName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestImageName();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(15)
    void testCreateProductGalleryBadRequestImageFormat() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestImageFormat();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(16)
    void testCreateProductGalleryNotFoundProductName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormNotFoundProductName();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(17)
    void testCreateProductGalleryNotFoundColorName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormNotFoundColorName();

        client.post()
                .uri("/product-gallery")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(18)
    void testUpdateProductGallery() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormUpdateRequest();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(res -> {
                    try {
                        JsonNode json = objectMapper.readTree(res.getResponseBody());
                        assertAll(
                            () -> assertNotNull(json),
                            () -> assertEquals(8L, json.path("id").asLong()),
                            () -> assertEquals(5L, json.path("product").path("id").asLong()),
                            () -> assertEquals(1L, json.path("colorAttribute").path("id").asLong()),
                            () -> assertTrue(
                                json.path("imageUrl").asText().contains(
                                    "https://product-gallery-images-ecommerce-test.s3.us-east-2.amazonaws.com")),
                            () -> assertTrue(json.path("imageUrl").asText().contains("ok-update-example.jpg"))
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(19)
    void testUpdateProductGalleryBadRequestEmptyProduct() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyProductName();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(20)
    void testUpdateProductGalleryBadRequestEmptyColor() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyColorName();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(21)
    void testUpdateProductGalleryBadRequestEmptyImage() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestEmptyImage();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(22)
    void testUpdateProductGalleryBadRequestImageName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestImageName();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(23)
    void testUpdateProductGalleryBadRequestImageFormat() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormBadRequestImageFormat();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(24)
    void testUpdateProductGalleryNotFound() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormUpdateRequest();
        String notExistingId = "100";

        client.put()
                .uri("/product-gallery/" + notExistingId)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(25)
    void testUpdateProductGalleryNotFoundProductName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormNotFoundProductName();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(26)
    void testUpdateProductGalleryNotFoundColorName() throws IOException {
        MultipartBodyBuilder builder = createProductGalleryMultipartFormNotFoundColorName();

        client.put()
                .uri("/product-gallery/8")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(27)
    void testDeleteProductGallery() {
        client.delete()
                .uri("/product-gallery/8")
                .exchange()
                .expectStatus().isNoContent();

        client.get()
                .uri("/product-gallery")
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
                            () -> assertEquals(7, json.size())
                        );
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Test
    @Order(28)
    void testGetDeletedProductGallery() {
        client.get()
                .uri("/product-gallery/8")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testBucketName() {
        assertEquals("product-gallery-images-ecommerce-test", env.getProperty("aws.s3.bucketName"));
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
