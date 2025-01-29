package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class ProductCreationDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @Min(1)
    private Long categoryId;

    @Min(1)
    private Long brandId;

    @Column(name = "cover")
    @NotBlank
    private String cover;

    public ProductCreationDto() {
    }

    public ProductCreationDto(
            @NotBlank String name, 
            @NotBlank String description, 
            @Min(0) Long categoryId, 
            @Min(0) Long brandId, 
            @NotBlank String cover
        ) {
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.cover = cover;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

}
