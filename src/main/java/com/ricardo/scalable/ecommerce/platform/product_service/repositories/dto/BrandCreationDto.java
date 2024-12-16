package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.NotBlank;

public class BrandCreationDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String logoUrl;

    public BrandCreationDto() {
    }

    public BrandCreationDto(@NotBlank String name, @NotBlank String description, @NotBlank String logoUrl) {
        this.name = name;
        this.description = description;
        this.logoUrl = logoUrl;
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

}
