package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductSkuCreationDto {

    @Min(1)
    private Long productId;

    @Min(1)
    private Long sizeAttributeId;

    @Min(1)
    private Long colorAttributeId;

    @NotBlank
    private String sku;

    @DecimalMin("0.00")
    private Double price;

    private Integer stock;

    private Boolean isActive;

    private Boolean isFeatured;

    private Boolean isOnSale;

    public ProductSkuCreationDto() {
    }

    public ProductSkuCreationDto(Long productId, Long sizeAttributeId, Long colorAttributeId, String sku, Double price,
            Integer stock, Boolean isActive, Boolean isFeatured, Boolean isOnSale) {
        this.productId = productId;
        this.sizeAttributeId = sizeAttributeId;
        this.colorAttributeId = colorAttributeId;
        this.sku = sku;
        this.price = price;
        this.stock = stock;
        this.isActive = isActive;
        this.isFeatured = isFeatured;
        this.isOnSale = isOnSale;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getSizeAttributeId() {
        return sizeAttributeId;
    }

    public void setSizeAttributeId(Long sizeAttributeId) {
        this.sizeAttributeId = sizeAttributeId;
    }

    public Long getColorAttributeId() {
        return colorAttributeId;
    }

    public void setColorAttributeId(Long colorAttributeId) {
        this.colorAttributeId = colorAttributeId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
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
