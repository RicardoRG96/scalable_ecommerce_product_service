package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

public class DiscountCodeControllerTestData {

    public static DiscountCodeDto createDiscountCodeDto() {
        DiscountCodeDto discountCode = new DiscountCodeDto();
        discountCode.setCode("20OFFLOYALCUSTOMER");
        discountCode.setDiscountId(3L);
        discountCode.setUsageLimit(100);

        return discountCode;
    }

    public static DiscountCodeDto createDiscountCodeToUpdate() {
        DiscountCodeDto discountCode = new DiscountCodeDto();
        discountCode.setCode("20OFF-LOYAL-CUSTOMER-APRIL");
        discountCode.setDiscountId(3L);
        discountCode.setUsageLimit(120);

        return discountCode;
    }

}
