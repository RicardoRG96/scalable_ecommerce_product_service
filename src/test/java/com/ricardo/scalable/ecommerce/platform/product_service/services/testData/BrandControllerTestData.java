package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;

public class BrandControllerTestData {

    public static Brand createBrand001() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Lee");
        brand.setDescription("Marca líder en moda");
        brand.setLogoUrl("https://example.com/lee_logo.png");
        brand.setCreatedAt(Timestamp.from(Instant.now()));
        brand.setUpdatedAt(Timestamp.from(Instant.now()));
        return brand;
    }

}
