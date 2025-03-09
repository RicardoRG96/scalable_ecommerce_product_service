package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DiscountDto {

    @NotBlank
    private String discountType;

    @NotNull
    private Double discountValue;

    @NotNull
    private LocalDateTime startDate;

    @NotNull
    private LocalDateTime endDate;

    private Boolean isActive;

    @NotNull
    private List<Long> productSkuIds;

    public DiscountDto() {
    }

    public DiscountDto(String discountType, Double discountValue, LocalDateTime startDate,
            LocalDateTime endDate, Boolean isActive, List<Long> productSkuIds) {
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
        this.productSkuIds = productSkuIds;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public Double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(Double discountValue) {
        this.discountValue = discountValue;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public List<Long> getProductSkuIds() {
        return productSkuIds;
    }

    public void setProductSkuIds(List<Long> productSkuIds) {
        this.productSkuIds = productSkuIds;
    }

}
