package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductGalleryCreationDto {

    @Min(1)
    @NotNull
    private Long productId;

    @Min(1)
    @NotNull
    private Long colorAttributeId;

    @NotBlank
    private String imageUrl;

    public ProductGalleryCreationDto() {
    }

    public ProductGalleryCreationDto(Long productId, Long colorAttributeId, String imageUrl) {
        this.productId = productId;
        this.colorAttributeId = colorAttributeId;
        this.imageUrl = imageUrl;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getColorAttributeId() {
        return colorAttributeId;
    }

    public void setColorAttributeId(Long colorAttributeId) {
        this.colorAttributeId = colorAttributeId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

}
