package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;

public class CategoryControllerTestData {

    public static Category createCategory002() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Tecnologia");
        category.setDescription("Computadores portatiles");
        category.setParent(null);
        category.setCreatedAt(Timestamp.from(Instant.now()));
        category.setUpdatedAt(Timestamp.from(Instant.now()));

        return category;
    }

}
