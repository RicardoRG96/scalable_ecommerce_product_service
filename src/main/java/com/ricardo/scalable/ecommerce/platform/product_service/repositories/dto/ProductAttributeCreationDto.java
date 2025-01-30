package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.NotBlank;

public class ProductAttributeCreationDto {

    @NotBlank
    private String type;

    @NotBlank
    private String value;

    public ProductAttributeCreationDto() {
    }

    public ProductAttributeCreationDto(@NotBlank String type, @NotBlank String value) {
        this.type = type;
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
