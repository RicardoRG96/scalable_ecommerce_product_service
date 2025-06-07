package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;

@DataJpaTest
@ActiveProfiles("test")
public class DiscountRepositoryTest {

    @Autowired
    private DiscountRepository discountRepository;

    @Test
    void testFindByProductSkuId() {
        Optional<List<Discount>> discounts = discountRepository.findByProductSkuId(1L);

        assertAll(
            () -> assertTrue(discounts.isPresent()),
            () -> assertFalse(discounts.orElseThrow().isEmpty()),
            () -> assertEquals(3, discounts.orElseThrow().size()),
            () -> assertEquals("AMOUNT", discounts.orElseThrow().get(0).getDiscountType().name()),
            () -> assertEquals("PERCENTAGE", discounts.orElseThrow().get(1).getDiscountType().name()),
            () -> assertEquals("AMOUNT", discounts.orElseThrow().get(2).getDiscountType().name())
        );
    }

    @Test
    void testVerifyValidityPeriod() {
        Optional<Discount> validDiscount = discountRepository.verifyValidityPeriod(1L);
        Optional<Discount> invalidDiscount = discountRepository.verifyValidityPeriod(4L);

        assertAll(
            () -> assertTrue(validDiscount.isPresent()),
            () -> assertFalse(invalidDiscount.isPresent()),
            () -> assertEquals("AMOUNT", validDiscount.orElseThrow().getDiscountType().name()),
            () -> assertEquals(10.00, validDiscount.orElseThrow().getDiscountValue()),
            () -> assertThrows(
                NoSuchElementException.class, 
                () -> invalidDiscount.orElseThrow().getDiscountType()    
            )
        );
    }

    @Test
    void testCheckOverlappingDiscount() {
        LocalDateTime overlappingDiscountStartDate = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        LocalDateTime overlappingDiscountEndDate = LocalDateTime.of(2025, 3, 31, 23, 59, 59);
        int overlappingDiscount = discountRepository.checkOverlappingDiscount(1L, overlappingDiscountStartDate, overlappingDiscountEndDate);

        LocalDateTime notOverlappingDiscountStartDate = LocalDateTime.of(2025, 2, 1, 0, 0, 0);
        LocalDateTime notOverlappingDiscountEndDate = LocalDateTime.of(2025, 2, 28, 23, 59, 59);
        int notOverlappingDiscount = discountRepository.checkOverlappingDiscount(3L, notOverlappingDiscountStartDate, notOverlappingDiscountEndDate);

        assertAll(
            () -> assertEquals(overlappingDiscount, 2),
            () -> assertEquals(notOverlappingDiscount, 1)
        );
    }

}
