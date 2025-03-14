package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.time.LocalDateTime;
import java.util.List;

import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

public class DiscountControllerTestData {

    public static DiscountDto createDiscountDto() {
        DiscountDto discount = new DiscountDto();
        LocalDateTime startDate = LocalDateTime.of(2025, 03, 14, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 03, 21, 23, 59, 59);

        discount.setDiscountType("fixed_amount");
        discount.setDiscountValue(8.00);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setIsActive(true);
        discount.setProductSkuIds(List.of(5L, 6L));

        return discount;
    }

    public static DiscountDto createDiscountToUpdate() {
        DiscountDto discount = new DiscountDto();
        LocalDateTime startDate = LocalDateTime.of(2025, 03, 14, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 03, 22, 23, 59, 59);

        discount.setDiscountType("fixed_amount");
        discount.setDiscountValue(9.00);
        discount.setStartDate(startDate);
        discount.setEndDate(endDate);
        discount.setIsActive(true);
        discount.setProductSkuIds(List.of(5L, 6L));

        return discount;
    }

}
