package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductCreationDto {

    @NotBlank
    private String sku;

    @NotBlank
    private String upc;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(1)
    private Long categoryId;

    @Min(1)
    private Long brandId;

    @DecimalMin("0.00")
    private Double price;

    @Min(1)
    private Integer stock;

    @Column(name = "image_url")
    @NotBlank
    private String imageUrl;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "is_on_sale")
    private Boolean isOnSale;

    public ProductCreationDto() {
    }

    public ProductCreationDto(@NotBlank String sku, @NotBlank String upc, @NotBlank String name,
            @NotBlank String description, @Min(0) Long categoryId, @Min(0) Long brandId,
            @DecimalMin("0.00") Double price, @Min(0) Integer stock, @NotBlank String imageUrl, Boolean isActive,
            Boolean isFeatured, Boolean isOnSale) {
        this.sku = sku;
        this.upc = upc;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.isFeatured = isFeatured;
        this.isOnSale = isOnSale;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsFeatured() {
        return isFeatured;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean isOnSale) {
        this.isOnSale = isOnSale;
    }

}
