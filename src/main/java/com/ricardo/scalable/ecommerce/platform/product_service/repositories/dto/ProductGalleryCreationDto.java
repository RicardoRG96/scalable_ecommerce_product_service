package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;

public class ProductGalleryCreationDto {

    @NotBlank
    private String productName;

    @NotBlank
    private String colorName;

    private MultipartFile image;

    public ProductGalleryCreationDto() {
    }

    public ProductGalleryCreationDto(String productName, String colorName, MultipartFile image) {
        this.productName = productName;
        this.colorName = colorName;
        this.image = image;
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

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

}
