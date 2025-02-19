package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;

public class ProductSkuServiceTestData {

    public static Iterable<ProductSku> createListOfProductSku() {
        ProductSku productSku1 = createProductSku001().orElseThrow();
        ProductSku productSku2 = createProductSku002().orElseThrow();
        ProductSku productSku3 = createProductSku003().orElseThrow();
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku5 = createProductSku005().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();
        ProductSku productSku7 = createProductSku007().orElseThrow();
        
        return List.of(
            productSku1, 
            productSku2, 
            productSku3, 
            productSku4, 
            productSku5, 
            productSku6, 
            productSku7
        );
    }

    public static Optional<ProductSku> createProductSku001() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct001().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productSku.setId(1L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU001");
        productSku.setPrice(1000.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku002() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct002().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(2L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU002");
        productSku.setPrice(899.99);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku003() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct003().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute007().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productSku.setId(3L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU003");
        productSku.setPrice(1299.99);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku004() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productSku.setId(4L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU004");
        productSku.setPrice(35.00);
        productSku.setStock(10);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku005() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute005().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(5L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU005");
        productSku.setPrice(25.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku006() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productSku.setId(6L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU006");
        productSku.setPrice(50.00);
        productSku.setStock(50);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku007() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizeAttribute = createProductAttribute006().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productSku.setId(7L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU007");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductId1() {
        ProductSku productSku1 = createProductSku001().orElseThrow();

        return Optional.of(List.of(productSku1));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductId2() {
        ProductSku productSku2 = createProductSku002().orElseThrow();

        return Optional.of(List.of(productSku2));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductId3() {
        ProductSku productSku3 = createProductSku003().orElseThrow();

        return Optional.of(List.of(productSku3));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductId4() {
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();

        return Optional.of(List.of(productSku4, productSku6));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductId5() {
        ProductSku productSku5 = createProductSku005().orElseThrow();
        ProductSku productSku7 = createProductSku007().orElseThrow();

        return Optional.of(List.of(productSku5, productSku7));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductIdAndSizeAttributeId() {
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();
        return Optional.of(List.of(productSku4, productSku6));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByProductIdAndColorAttributeId() {
        ProductSku productSku5 = createProductSku005().orElseThrow();
        ProductSku productSku7 = createProductSku007().orElseThrow();
        return Optional.of(List.of(productSku5, productSku7));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuBySizeAttributeId() {
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();
        return Optional.of(List.of(productSku4, productSku6));
    }

    public static Optional<List<ProductSku>> createListOfProductSkuByColorAttributeId() {
        ProductSku productSku1 = createProductSku001().orElseThrow();
        ProductSku productSku4 = createProductSku004().orElseThrow();
        return Optional.of(List.of(productSku1, productSku4));
    }

    public static ProductSkuCreationDto createProductSkuCreationDto() {
        ProductSkuCreationDto productSkuCreationDto = new ProductSkuCreationDto(
            4L, 5L, 3L, "SKU008", 30.00, 100, true, true, false
        );

        return productSkuCreationDto;
    }

    public static ProductSku createProductSkuCreationResponse() {
        ProductSku productSku = new ProductSku();
        productSku.setId(8L);
        productSku.setProduct(createProduct004().orElseThrow());
        productSku.setSizeAttribute(createProductAttribute005().orElseThrow());
        productSku.setColorAttribute(createProductAttribute003().orElseThrow());
        productSku.setSku("SKU008");
        productSku.setPrice(30.00);
        productSku.setStock(100);
        productSku.setIsActive(true);
        productSku.setIsFeatured(true);
        productSku.setIsOnSale(false);

        return productSku;
    }

    public static ProductSku createUpdatedProductSku() {
        ProductSku productSku = new ProductSku();
        productSku.setId(1L);
        productSku.setProduct(createProduct001().orElseThrow());
        productSku.setSizeAttribute(createProductAttribute007().orElseThrow());
        productSku.setColorAttribute(createProductAttribute003().orElseThrow());
        productSku.setSku("SKU001");
        productSku.setPrice(50.00);
        productSku.setStock(10);
        productSku.setIsActive(true);
        productSku.setIsFeatured(true);
        productSku.setIsOnSale(true);

        return productSku;
    }

}
