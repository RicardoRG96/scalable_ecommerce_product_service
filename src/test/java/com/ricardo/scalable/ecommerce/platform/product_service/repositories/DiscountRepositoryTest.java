package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Discount;

@DataJpaTest
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
            () -> assertEquals("fixed_amount", discounts.orElseThrow().get(0).getDiscountType()),
            () -> assertEquals("percentage", discounts.orElseThrow().get(1).getDiscountType()),
            () -> assertEquals("free_shipping", discounts.orElseThrow().get(2).getDiscountType())
        );
    }

    @Test
    void testVerifyValidityPeriod() {
        Optional<Discount> validDiscount = discountRepository.verifyValidityPeriod(1L);
        Optional<Discount> invalidDiscount = discountRepository.verifyValidityPeriod(4L);

        assertAll(
            () -> assertTrue(validDiscount.isPresent()),
            () -> assertFalse(invalidDiscount.isPresent()),
            () -> assertEquals("fixed_amount", validDiscount.orElseThrow().getDiscountType()),
            () -> assertEquals(10.00, validDiscount.orElseThrow().getDiscountValue()),
            () -> assertThrows(
                NoSuchElementException.class, 
                () -> invalidDiscount.orElseThrow().getDiscountType()    
            )
        );
    }

}
