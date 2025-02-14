package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;

public class ProductGalleryServiceTestData {

    public static Iterable<ProductGallery> createListOfProductGallery() {
        ProductGallery productGallery1 = createProductGallery001().orElseThrow();
        ProductGallery productGallery2 = createProductGallery002().orElseThrow();
        ProductGallery productGallery3 = createProductGallery003().orElseThrow();
        ProductGallery productGallery4 = createProductGallery004().orElseThrow();
        ProductGallery productGallery5 = createProductGallery005().orElseThrow();
        ProductGallery productGallery6 = createProductGallery006().orElseThrow();
        ProductGallery productGallery7 = createProductGallery007().orElseThrow();

        return List.of(
            productGallery1, 
            productGallery2, 
            productGallery3, 
            productGallery4, 
            productGallery5,
            productGallery6,
            productGallery7
        );
    }

    public static Optional<ProductGallery> createProductGallery001() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct001().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productGallery.setId(1L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image1.png");

        return Optional.of(productGallery);
    }

    public static Optional<ProductGallery> createProductGallery002() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct002().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productGallery.setId(2L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image2.png");

        return Optional.of(productGallery);
    }

    public static Optional<ProductGallery> createProductGallery003() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct003().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productGallery.setId(3L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image3.png");

        return Optional.of(productGallery);
    }

    public static Optional<ProductGallery> createProductGallery004() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute003().orElseThrow();

        productGallery.setId(4L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image4.png");

        return Optional.of(productGallery);
    }

    public static Optional<ProductGallery> createProductGallery005() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct005().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productGallery.setId(5L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image5.png");

        return Optional.of(productGallery);
    }

    public static Optional<ProductGallery> createProductGallery006() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct004().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute001().orElseThrow();

        productGallery.setId(6L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image6.png");

        return Optional.of(productGallery);
    }


    public static Optional<ProductGallery> createProductGallery007() {
        ProductGallery productGallery = new ProductGallery();
        Product product = createProduct005().orElseThrow();
        ProductAttribute colorAttribute = createProductAttribute002().orElseThrow();

        productGallery.setId(7L);
        productGallery.setProduct(product);
        productGallery.setColorAttribute(colorAttribute);
        productGallery.setImageUrl("https://example.com/image7.png");

        return Optional.of(productGallery);
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByProductId1() {
        return Optional.of(
            List.of(createProductGallery001().orElseThrow())
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByProductId2() {
        return Optional.of(
            List.of(createProductGallery002().orElseThrow())
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByProductId3() {
        return Optional.of(
            List.of(createProductGallery003().orElseThrow())
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByProductId4() {
        return Optional.of(
            List.of(
                createProductGallery004().orElseThrow(),
                createProductGallery006().orElseThrow()
            )
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByProductId5() {
        return Optional.of(
            List.of(
                createProductGallery005().orElseThrow(),
                createProductGallery007().orElseThrow()
            )
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByColorAttributeId1() {
        return Optional.of(
            List.of(
                createProductGallery003().orElseThrow(),
                createProductGallery006().orElseThrow()
            )
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByColorAttributeId2() {
        return Optional.of(
            List.of(
                createProductGallery002().orElseThrow(),
                createProductGallery005().orElseThrow(),
                createProductGallery007().orElseThrow()
            )
        );
    }

    public static Optional<List<ProductGallery>> createListOfProductGalleryByColorAttributeId3() {
        return Optional.of(
            List.of(
                createProductGallery001().orElseThrow(),
                createProductGallery004().orElseThrow()
            )
        );
    }

    public static ProductGalleryCreationDto createProductGalleryCreationDto() {
        ProductGalleryCreationDto productGalleryCreationDto = new ProductGalleryCreationDto();
        productGalleryCreationDto.setProductId(4L);
        productGalleryCreationDto.setColorAttributeId(3L);
        productGalleryCreationDto.setImageUrl("https://example.com/image8.png");

        return productGalleryCreationDto;
    }

    public static ProductGallery createProductGalleryCreationResponse() {
        ProductGallery productGallery = new ProductGallery();

        productGallery.setId(8L);
        productGallery.setProduct(createProduct004().orElseThrow());
        productGallery.setColorAttribute(createProductAttribute003().orElseThrow());
        productGallery.setImageUrl("https://example.com/image8.png");
        productGallery.setCreatedAt(Timestamp.from(Instant.now()));
        productGallery.setUpdatedAt(Timestamp.from(Instant.now()));

        return productGallery;
    }
    
}
