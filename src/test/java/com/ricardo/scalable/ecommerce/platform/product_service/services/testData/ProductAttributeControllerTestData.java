package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;

public class ProductAttributeControllerTestData {

    public static ProductAttribute createProductAttribute002() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(2L);
        productAttribute.setType("color");
        productAttribute.setValue("blue");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));
        return productAttribute;
    }

}
