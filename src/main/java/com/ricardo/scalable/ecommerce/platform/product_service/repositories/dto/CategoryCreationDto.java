package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.NotBlank;

public class CategoryCreationDto {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private Long parentId;

    public CategoryCreationDto() {
    }

    public CategoryCreationDto(@NotBlank String name, @NotBlank String description, Long parentId) {
        this.name = name;
        this.description = description;
        this.parentId = parentId;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

}
