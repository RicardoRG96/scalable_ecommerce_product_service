package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class ProductGalleryCreationDto {

    // @Min(1)
    // @NotNull
    // private Long productId;

    // @Min(1)
    // @NotNull
    // private Long colorAttributeId;

    // @NotBlank
    // private String imageUrl;

    @NotBlank
    private String productName;

    @NotBlank
    private String colorName;

    private List<MultipartFile> images;

    public ProductGalleryCreationDto() {
    }

    public ProductGalleryCreationDto(String productName, String colorName, List<MultipartFile> images) {
        this.productName = productName;
        this.colorName = colorName;
        this.images = images;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

}
