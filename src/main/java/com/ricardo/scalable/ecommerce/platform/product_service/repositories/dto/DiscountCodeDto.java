package com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DiscountCodeDto {

    @NotBlank
    private String code;

    @NotNull
    private Long discountId;

    @NotNull
    private Integer usageLimit;

    public DiscountCodeDto() {
    }

    public DiscountCodeDto(@NotBlank String code, @NotNull Long discountId, @NotNull Integer usageLimit) {
        this.code = code;
        this.discountId = discountId;
        this.usageLimit = usageLimit;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Long discountId) {
        this.discountId = discountId;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

}
