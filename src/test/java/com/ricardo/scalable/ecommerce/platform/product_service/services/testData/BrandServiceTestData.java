package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

public class BrandServiceTestData {

    public static Iterable<Brand> createListOfBrands() {
        Brand brand1 = createBrand001().orElseThrow();
        Brand brand2 = createBrand002().orElseThrow();

        return List.of(brand1, brand2);
    }

    public static Optional<Brand> createBrand001() {
        Brand brand = new Brand(
            1L, 
            "Apple", 
            "Marca Apple", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand002() {
        Brand brand = new Brand(
            2L, 
            "Samsung", 
            "Marca Samsung", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand003() {
        Brand brand = new Brand(
            3L, 
            "Americanino", 
            "Marca Americanino", 
            "logo5.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createUnknownBrand() {
        Brand brand = new Brand(
            4L, 
            "Unknown", 
            "Unknown", 
            "Unknown", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static BrandCreationDto createBrandCreationDto() {
        BrandCreationDto brandCreationDto = new BrandCreationDto();
        brandCreationDto.setName("Nike");
        brandCreationDto.setDescription("Marca nike");
        brandCreationDto.setLogoUrl("logo2.png");

        return brandCreationDto;
    }

    public static Brand createBrandCreationResponse() {
        Brand brand = new Brand(
            8L, 
            "Nike", 
            "Marca nike", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return brand;
    }

}
