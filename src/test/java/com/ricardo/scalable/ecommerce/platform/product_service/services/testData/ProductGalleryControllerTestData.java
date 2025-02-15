package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;

public class ProductGalleryControllerTestData {

    public static ProductGalleryCreationDto createProductGalleryCreationDto() {
        ProductGalleryCreationDto productGallery = new ProductGalleryCreationDto();
        productGallery.setProductId(5L);
        productGallery.setColorAttributeId(1L);
        productGallery.setImageUrl("https://example.com/images/polera-puma-red.jpg");

        return productGallery;
    }

}
