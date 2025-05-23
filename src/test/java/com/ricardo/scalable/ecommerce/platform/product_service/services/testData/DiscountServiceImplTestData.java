package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.DiscountType;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.*;

public class DiscountServiceImplTestData {

    public static List<Discount> createListOfDiscounts() {
        Discount discount1 = createDiscount001().orElseThrow();
        Discount discount2 = createDiscount002().orElseThrow();
        Discount discount3 = createDiscount003().orElseThrow();
        Discount discount4 = createDiscount004().orElseThrow();
        Discount discount5 = createDiscount005().orElseThrow();

        return List.of(discount1, discount2, discount3, discount4, discount5);
    }

    public static Optional<Discount> createDiscount001() {
        Discount discount = new Discount();
        List<ProductSku> productSkus = List.of(
            createProductSku001().orElseThrow(),
            createProductSku002().orElseThrow()
        );

        discount.setId(1L);
        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDiscountValue(10.0);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 10, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 4, 10, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(productSkus);
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discount);
    }

    public static Optional<Discount> createDiscount002() {
        Discount discount = new Discount();
        List<ProductSku> productSkus = List.of(
            createProductSku003().orElseThrow(),
            createProductSku004().orElseThrow()
        );

        discount.setId(2L);
        discount.setDiscountType(DiscountType.AMOUNT);
        discount.setDiscountValue(10.00);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 1, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 9, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(productSkus);
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discount);
    }

    public static Optional<Discount> createDiscount003() {
        Discount discount = new Discount();
        List<ProductSku> productSkus = List.of(
            createProductSku005().orElseThrow(),
            createProductSku006().orElseThrow()
        );

        discount.setId(3L);
        discount.setDiscountType(DiscountType.AMOUNT);
        discount.setDiscountValue(5.00);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 5, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 20, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(productSkus);
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discount);
    }

    public static Optional<Discount> createDiscount004() {
        Discount discount = new Discount();
        List<ProductSku> productSkus = List.of(
            createProductSku004().orElseThrow(),
            createProductSku007().orElseThrow()
        );

        discount.setId(4L);
        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDiscountValue(15.0);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 1, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 8, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(productSkus);
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discount);
    }

    public static Optional<Discount> createDiscount005() {
        Discount discount = new Discount();
        List<ProductSku> productSkus = List.of(
            createProductSku003().orElseThrow(),
            createProductSku004().orElseThrow()
        );

        discount.setId(5L);
        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDiscountValue(20.0);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 10, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 4, 10, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(productSkus);
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(discount);
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId1() {
        Discount discount = createDiscount001().orElseThrow();

        return Optional.of(List.of(discount));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId2() {
        Discount discount = createDiscount001().orElseThrow();

        return Optional.of(List.of(discount));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId3() {
        Discount discount1 = createDiscount002().orElseThrow();
        Discount discount2 = createDiscount005().orElseThrow();

        return Optional.of(List.of(discount1, discount2));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId4() {
        Discount discount1 = createDiscount002().orElseThrow();
        Discount discount2 = createDiscount004().orElseThrow();
        Discount discount3 = createDiscount005().orElseThrow();

        return Optional.of(List.of(discount1, discount2, discount3));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId5() {
        Discount discount = createDiscount003().orElseThrow();

        return Optional.of(List.of(discount));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId6() {
        Discount discount = createDiscount003().orElseThrow();

        return Optional.of(List.of(discount));
    }

    public static Optional<List<Discount>> createListOfDiscountByProductSkuId7() {
        Discount discount = createDiscount004().orElseThrow();

        return Optional.of(List.of(discount));
    }

    public static Optional<List<Discount>> createListOfDiscountByType() {
        Discount discount1 = createDiscount001().orElseThrow();
        Discount discount2 = createDiscount005().orElseThrow();

        return Optional.of(List.of(discount1, discount2));
    }

    public static Optional<List<Discount>> createListOfDiscountByValue() {
        Discount discount1 = createDiscount001().orElseThrow();
        Discount discount2 = createDiscount002().orElseThrow();

        return Optional.of(List.of(discount1, discount2));
    }

    public static DiscountDto createDiscountDtoCreation() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setDiscountType("amount");
        discountDto.setDiscountValue(12.0);
        discountDto.setStartDate(LocalDateTime.of(2025, 3, 10, 0, 0, 0));
        discountDto.setEndDate(LocalDateTime.of(2025, 4, 2, 23, 59, 59));
        discountDto.setIsActive(true);
        discountDto.setProductSkuIds(List.of(6L, 7L));

        return discountDto;
    }

    public static Discount createDiscountCreationResponse() {
        Discount discount = new Discount();
        discount.setId(6L);
        discount.setDiscountType(DiscountType.AMOUNT);
        discount.setDiscountValue(12.0);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 10, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 4, 2, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(List.of(
            createProductSku006().orElseThrow(),
            createProductSku007().orElseThrow()
        ));
        discount.setCreatedAt(Timestamp.from(Instant.now()));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return discount;
    }

    public static DiscountDto createDiscountDtoUpdate() {
        DiscountDto discountDto = new DiscountDto();
        discountDto.setDiscountType("percentage");
        discountDto.setDiscountValue(25.0);
        discountDto.setStartDate(LocalDateTime.of(2025, 3, 10, 0, 0, 0));
        discountDto.setEndDate(LocalDateTime.of(2025, 4, 12, 23, 59, 59));
        discountDto.setIsActive(true);
        discountDto.setProductSkuIds(List.of(3L, 4L));

        return discountDto;
    }

    public static Discount createDiscountUpdatedResponse() {
        Discount discount = new Discount();
        discount.setId(5L);
        discount.setDiscountType(DiscountType.PERCENTAGE);
        discount.setDiscountValue(25.0);
        discount.setStartDate(Timestamp.valueOf(LocalDateTime.of(2025, 3, 10, 0, 0, 0)));
        discount.setEndDate(Timestamp.valueOf(LocalDateTime.of(2025, 4, 12, 23, 59, 59)));
        discount.setIsActive(true);
        discount.setProductSkus(List.of(
            createProductSku003().orElseThrow(),
            createProductSku004().orElseThrow()
        ));
        discount.setUpdatedAt(Timestamp.from(Instant.now()));

        return discount;
    }

}
