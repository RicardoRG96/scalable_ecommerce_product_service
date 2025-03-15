package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.*;

public class DiscountCodeServiceImplTestData {

    public static List<DiscountCode> createListOfDiscountCodes() {
        DiscountCode discountCode1 = createDiscountCode001().orElseThrow();
        DiscountCode discountCode2 = createDiscountCode002().orElseThrow();
        DiscountCode discountCode3 = createDiscountCode003().orElseThrow();
        DiscountCode discountCode4 = createDiscountCode004().orElseThrow();
        DiscountCode discountCode5 = createDiscountCode005().orElseThrow();

        return List.of(
            discountCode1, 
            discountCode2,
            discountCode3,
            discountCode4,
            discountCode5
        );
    }

    public static Optional<DiscountCode> createDiscountCode001() {
        DiscountCode discountCode = new DiscountCode();
        Discount discount = createDiscount001().orElseThrow();

        discountCode.setId(1L);
        discountCode.setCode("MATEO2025");
        discountCode.setUsageLimit(50);
        discountCode.setDiscount(discount);
        discountCode.setUsedCount(10);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discountCode);
    }

    public static Optional<DiscountCode> createDiscountCode002() {
        DiscountCode discountCode = new DiscountCode();
        Discount discount = createDiscount002().orElseThrow();

        discountCode.setId(2L);
        discountCode.setCode("SUMMER2025");
        discountCode.setUsageLimit(70);
        discountCode.setDiscount(discount);
        discountCode.setUsedCount(10);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discountCode);
    }


    public static Optional<DiscountCode> createDiscountCode003() {
        DiscountCode discountCode = new DiscountCode();
        Discount discount = createDiscount003().orElseThrow();

        discountCode.setId(3L);
        discountCode.setCode("FREESHIPPING2025");
        discountCode.setUsageLimit(100);
        discountCode.setDiscount(discount);
        discountCode.setUsedCount(0);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discountCode);
    }

    public static Optional<DiscountCode> createDiscountCode004() {
        DiscountCode discountCode = new DiscountCode();
        Discount discount = createDiscount004().orElseThrow();

        discountCode.setId(4L);
        discountCode.setCode("LOYALTY2025");
        discountCode.setUsageLimit(70);
        discountCode.setDiscount(discount);
        discountCode.setUsedCount(0);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discountCode);
    }

    public static Optional<DiscountCode> createDiscountCode005() {
        DiscountCode discountCode = new DiscountCode();
        Discount discount = createDiscount001().orElseThrow();

        discountCode.setId(5L);
        discountCode.setCode("10OFFMARZO");
        discountCode.setUsageLimit(100);
        discountCode.setDiscount(discount);
        discountCode.setUsedCount(0);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discountCode);
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByDiscountId1() {
        DiscountCode discountCode1 = createDiscountCode001().orElseThrow();
        DiscountCode discountCode5 = createDiscountCode005().orElseThrow();

        return Optional.of(
            List.of(discountCode1, discountCode5)
        );
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByDiscountId2() {
        DiscountCode discountCode2 = createDiscountCode002().orElseThrow();

        return Optional.of(
            List.of(discountCode2)
        );
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByDiscountId3() {
        DiscountCode discountCode3 = createDiscountCode003().orElseThrow();

        return Optional.of(
            List.of(discountCode3)
        );
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByDiscountId4() {
        DiscountCode discountCode4 = createDiscountCode004().orElseThrow();

        return Optional.of(
            List.of(discountCode4)
        );
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByUsageLimit() {
        DiscountCode discountCode2 = createDiscountCode002().orElseThrow();
        DiscountCode discountCode4 = createDiscountCode004().orElseThrow();
        
        return Optional.of(
            List.of(discountCode2, discountCode4)
        );
    }

    public static Optional<List<DiscountCode>> createListOfDiscountCodeByUsedCount() {
        DiscountCode discountCode1 = createDiscountCode001().orElseThrow();
        DiscountCode discountCode2 = createDiscountCode002().orElseThrow();
        
        return Optional.of(
            List.of(discountCode1, discountCode2)
        );
    }

    public static DiscountCodeDto createDiscountCodeDtoCreation() {
        DiscountCodeDto discountCodeDto = new DiscountCodeDto();
        discountCodeDto.setCode("20OFFMARZO2025");
        discountCodeDto.setDiscountId(5L);
        discountCodeDto.setUsageLimit(100);

        return discountCodeDto;
    }

    public static DiscountCode createDiscountCodeCreationResponse() {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setId(6L);
        discountCode.setCode("20OFFMARZO2025");
        discountCode.setDiscount(createDiscount005().orElseThrow());
        discountCode.setUsageLimit(100);
        discountCode.setUsedCount(0);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return discountCode;
    }

    public static DiscountCodeDto createDiscountCodeDtoUpdate() {
        DiscountCodeDto discountCodeDto = new DiscountCodeDto();
        discountCodeDto.setCode("20OFFMARZO-ABRIL2025");
        discountCodeDto.setDiscountId(5L);
        discountCodeDto.setUsageLimit(120);

        return discountCodeDto;
    }

    public static DiscountCode createDiscountCodeUpdateResponse() {
        DiscountCode discountCode = new DiscountCode();
        discountCode.setId(6L);
        discountCode.setCode("20OFFMARZO-ABRIL2025");
        discountCode.setDiscount(createDiscount005().orElseThrow());
        discountCode.setUsageLimit(120);
        discountCode.setUsedCount(0);
        discountCode.setCreatedAt(Timestamp.from(Instant.now()));
        discountCode.setUpdatedAt(Timestamp.from(Instant.now()));

        return discountCode;
    }

}
