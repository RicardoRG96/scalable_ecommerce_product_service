// package com.ricardo.scalable.ecommerce.platform.product_service.integrationTests;

// import static org.junit.jupiter.api.Assertions.*;
// import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

// import java.io.IOException;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.MethodOrderer;
// import org.junit.jupiter.api.Order;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestMethodOrder;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.core.env.Environment;
// import org.springframework.http.MediaType;
// import org.springframework.test.context.ActiveProfiles;
// import org.springframework.test.web.reactive.server.WebTestClient;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
// import com.ricardo.scalable.ecommerce.platform.product_service.integrationTestData.Data;
// import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;

// @ActiveProfiles("test")
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// @SpringBootTest(webEnvironment = RANDOM_PORT)
// public class ProductControllerTest {

//     @Autowired
//     private Environment env;

//     private ObjectMapper objectMapper;

//     @Autowired
//     private WebTestClient client;

//     @BeforeEach
//     void setUp() {
//         objectMapper = new ObjectMapper();
//     }

//     @Test
//     @Order(1)
//     void testGetByProductId() {
//         Product product = Data.createProduct001();

//         client.get()
//                 .uri("/1")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(response -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(response.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(product.getId(), json.path("id").asLong()),
//                             () -> assertEquals(product.getName(), json.path("name").asText()),
//                             () -> assertEquals(product.getDescription(), json.path("description").asText()),
//                             () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
//                             () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
//                             () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
//                             () -> assertEquals(product.getIsActive(), json.path("isActive").asBoolean())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });

//         String notExistingProductId = "50";

//         client.get()
//                 .uri("/" + notExistingProductId)
//                 .exchange()
//                 .expectStatus().isNotFound();
//     }

//     @Test
//     @Order(2)
//     void testGetByName() {
//         Product product = Data.createProduct001();

//         client.get()
//                 .uri("/name/iPhone 15")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(product.getSku(), json.path("sku").asText()),
//                             () -> assertEquals(product.getName(), json.path("name").asText()),
//                             () -> assertEquals(product.getDescription(), json.path("description").asText()),
//                             () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
//                             () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
//                             () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
//                             () -> assertEquals(product.getIsFeatured(), json.path("isFeatured").asBoolean())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });

//         String notExistingProductName = "Samsung Galaxy s22";

//         client.get()
//                 .uri("/product-name/" + notExistingProductName)
//                 .exchange()
//                 .expectStatus().isNotFound();
//     }

//     @Test
//     @Order(3)
//     void testGetBySku() {
//         Product product = Data.createProduct001();

//         client.get()
//                 .uri("/product-sku/SKU2210")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(product.getSku(), json.path("sku").asText()),
//                             () -> assertEquals(product.getName(), json.path("name").asText()),
//                             () -> assertEquals(product.getDescription(), json.path("description").asText()),
//                             () -> assertEquals(product.getCategory().getName(), json.path("category").path("name").asText()),
//                             () -> assertEquals(product.getBrand().getName(), json.path("brand").path("name").asText()),
//                             () -> assertEquals(product.getPrice(), json.path("price").asDouble()),
//                             () -> assertEquals(product.getStock(), json.path("stock").asInt()),
//                             () -> assertEquals(product.getIsOnSale(), json.path("isOnSale").asBoolean())

//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });

//         String notExistingSku = "12345646";

//         client.get()
//                 .uri("/product-sku/" + notExistingSku)
//                 .exchange()
//                 .expectStatus().isNotFound();
//     }

//     @Test
//     @Order(4)
//     void testGetAllProducts() {
//         Product product1 = Data.createProduct001();
//         Product product2 = Data.createProduct002();

//         client.get()
//                 .uri("/")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertTrue(json.isArray()),
//                             () -> assertEquals(3, json.size()),
//                             () -> assertEquals(product1.getSku(), json.get(0).path("sku").asText()),
//                             () -> assertEquals(product2.getSku(), json.get(1).path("sku").asText()),
//                             () -> assertEquals(product1.getName(), json.get(0).path("name").asText()),
//                             () -> assertEquals(product2.getName(), json.get(1).path("name").asText()),
//                             () -> assertEquals(product1.getDescription(), json.get(0).path("description").asText()),
//                             () -> assertEquals(product2.getDescription(), json.get(1).path("description").asText()),
//                             () -> assertEquals(product1.getCategory().getName(), json.get(0).path("category").path("name").asText()),
//                             () -> assertEquals(product2.getCategory().getName(), json.get(1).path("category").path("name").asText()),
//                             () -> assertEquals(product1.getBrand().getName(), json.get(0).path("brand").path("name").asText()),
//                             () -> assertEquals(product2.getBrand().getName(), json.get(1).path("brand").path("name").asText()),
//                             () -> assertEquals(product1.getPrice(), json.get(0).path("price").asDouble()),
//                             () -> assertEquals(product2.getPrice(), json.get(1).path("price").asDouble()),
//                             () -> assertEquals(true, json.get(0).path("isFeatured").asBoolean()),
//                             () -> assertEquals(false, json.get(1).path("isFeatured").asBoolean())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });
//     }

//     @Test
//     @Order(5)
//     void testCreateProduct() {
//         ProductCreationDto productCreationRequest = new ProductCreationDto();
//         productCreationRequest.setSku("pepe1321");
//         productCreationRequest.setUpc("upcexample");
//         productCreationRequest.setName("Audifonos Huawei");
//         productCreationRequest.setDescription("description example");
//         productCreationRequest.setCategoryId(3L);
//         productCreationRequest.setBrandId(4L);
//         productCreationRequest.setPrice(70.99); 
//         productCreationRequest.setStock(25);
//         productCreationRequest.setImageUrl("/logos/logo-huawei.png");
//         productCreationRequest.setIsActive(true);
//         productCreationRequest.setIsFeatured(true);
//         productCreationRequest.setIsOnSale(false);

//         client.post()
//                 .uri("/")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .bodyValue(productCreationRequest)
//                 .exchange()
//                 .expectStatus().isCreated()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(productCreationRequest.getSku(), json.path("sku").asText()),
//                             () -> assertEquals(productCreationRequest.getUpc(), json.path("upc").asText()),
//                             () -> assertEquals(productCreationRequest.getName(), json.path("name").asText()),
//                             () -> assertEquals("Decohogar", json.path("category").path("name").asText()),
//                             () -> assertEquals("Puma", json.path("brand").path("name").asText()),
//                             () -> assertEquals(productCreationRequest.getIsActive(), json.path("isActive").asBoolean()),
//                             () -> assertEquals(productCreationRequest.getIsFeatured(), json.path("isFeatured").asBoolean()),
//                             () -> assertEquals(productCreationRequest.getIsOnSale(), json.path("isOnSale").asBoolean())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });
//     }

//     @Test
//     @Order(6)
//     void testUpdateProduct() {
//         Product productUpdateRequest = Data.createProduct001();
//         productUpdateRequest.setName("iPhone 16");
//         productUpdateRequest.setPrice(2000.00);

//         client.put()
//                 .uri("/1")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .bodyValue(productUpdateRequest)
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(productUpdateRequest.getSku(), json.path("sku").asText()),
//                             () -> assertEquals(productUpdateRequest.getUpc(), json.path("upc").asText()),
//                             () -> assertEquals(productUpdateRequest.getName(), json.path("name").asText()),
//                             () -> assertEquals(productUpdateRequest.getCategory().getName(), json.path("category").path("name").asText()),
//                             () -> assertEquals(productUpdateRequest.getBrand().getName(), json.path("brand").path("name").asText()),
//                             () -> assertEquals(productUpdateRequest.getIsActive(), json.path("isActive").asBoolean()),
//                             () -> assertEquals(productUpdateRequest.getIsFeatured(), json.path("isFeatured").asBoolean()),
//                             () -> assertEquals(productUpdateRequest.getIsOnSale(), json.path("isOnSale").asBoolean())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });
//     }

//     @Test
//     @Order(7)
//     void testDeleteProduct() {
//         client.delete()
//                 .uri("/3")
//                 .exchange()
//                 .expectStatus().isNoContent();

//         client.get()
//                 .uri("/")
//                 .exchange()
//                 .expectStatus().isOk()
//                 .expectHeader().contentType(MediaType.APPLICATION_JSON)
//                 .expectBody()
//                 .consumeWith(res -> {
//                     try {
//                         JsonNode json = objectMapper.readTree(res.getResponseBody());
//                         assertAll(
//                             () -> assertNotNull(json),
//                             () -> assertEquals(3, json.size()),
//                             () -> assertTrue(json.isArray())
//                         );
//                     } catch (IOException ex) {
//                         ex.printStackTrace();
//                     }
//                 });

//         client.get()
//                 .uri("/3")
//                 .exchange()
//                 .expectStatus().isNotFound();
//     }

//     @Test
//     void testProfile() {
//         assertArrayEquals(new String[]{"test"}, env.getActiveProfiles());
//     }

//     @Test
//     void testApplicationPropertyFile() {
//         assertEquals("jdbc:h2:mem:public", env.getProperty("spring.datasource.url"));
//     }
// }
