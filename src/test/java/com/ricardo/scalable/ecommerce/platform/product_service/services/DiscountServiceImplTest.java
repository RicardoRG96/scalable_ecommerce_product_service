package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.*;

@SpringBootTest
public class DiscountServiceImplTest {

    @MockitoBean
    private DiscountRepository discountRepository;

    @Autowired
    private DiscountService discountService;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

    @Test
    void testFindById() {
        when(discountRepository.findById(1L)).thenReturn(createDiscount001());

        Optional<Discount> discount = discountService.findById(1L);

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(1L, discount.orElseThrow().getId()),
            () -> assertEquals("percentage", discount.orElseThrow().getDiscountType()),
            () -> assertEquals(10.0, discount.orElseThrow().getDiscountValue()),
            () -> assertEquals("2025-03-10 00:00:00.0", discount.orElseThrow().getStartDate().toString()),
            () -> assertEquals("2025-04-10 23:59:59.0", discount.orElseThrow().getEndDate().toString()),
            () -> assertTrue(discount.orElseThrow().getIsActive()),
            () -> assertEquals(2, discount.orElseThrow().getProductSkus().size())
        );
    }

    @Test
    void testFindByProductSkuId() {
        when(discountRepository.findByProductSkuId(4L)).thenReturn(createListOfDiscountByProductSkuId4());

        Optional<List<Discount>> discount = discountService.findByProductSkuId(4L);

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(3, discount.orElseThrow().size()),
            () -> assertEquals(2L, discount.orElseThrow().get(0).getId()),
            () -> assertEquals(4L, discount.orElseThrow().get(1).getId()),
            () -> assertEquals(5L, discount.orElseThrow().get(2).getId()),
            () -> assertEquals("fixed amount", discount.orElseThrow().get(0).getDiscountType()),
            () -> assertEquals("quantity discount", discount.orElseThrow().get(1).getDiscountType()),
            () -> assertEquals("percentage", discount.orElseThrow().get(2).getDiscountType())
        );
    }

    @Test
    void testFindByDiscountType() {
        when(discountRepository.findByDiscountType("percentage")).thenReturn(createListOfDiscountByType());

        Optional<List<Discount>> discount = discountService.findByDiscountType("percentage");

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(2, discount.orElseThrow().size()),
            () -> assertEquals(1L, discount.orElseThrow().get(0).getId()),
            () -> assertEquals(5L, discount.orElseThrow().get(1).getId()),
            () -> assertEquals(10.0, discount.orElseThrow().get(0).getDiscountValue()),
            () -> assertEquals(20.0, discount.orElseThrow().get(1).getDiscountValue())
        );
    }

    @Test
    void testFindByDiscountValue() {
        when(discountRepository.findByDiscountValue(10.0)).thenReturn(createListOfDiscountByValue());

        Optional<List<Discount>> discount = discountService.findByDiscountValue(10.0);

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(2, discount.orElseThrow().size()),
            () -> assertEquals(1L, discount.orElseThrow().get(0).getId()),
            () -> assertEquals(2L, discount.orElseThrow().get(1).getId()),
            () -> assertEquals("percentage", discount.orElseThrow().get(0).getDiscountType()),
            () -> assertEquals("fixed amount", discount.orElseThrow().get(1).getDiscountType())
        );
    }

    @Test
    void testVerifyValidityPeriod() {
        when(discountRepository.verifyValidityPeriod(1L)).thenReturn(createDiscount001());

        Optional<Discount> discount = discountService.verifyValidityPeriod(1L);

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(1L, discount.orElseThrow().getId()),
            () -> assertEquals("percentage", discount.orElseThrow().getDiscountType()),
            () -> assertEquals(10.0, discount.orElseThrow().getDiscountValue()),
            () -> assertEquals("2025-03-10 00:00:00.0", discount.orElseThrow().getStartDate().toString()),
            () -> assertEquals("2025-04-10 23:59:59.0", discount.orElseThrow().getEndDate().toString()),
            () -> assertTrue(discount.orElseThrow().getIsActive()),
            () -> assertEquals(2, discount.orElseThrow().getProductSkus().size())
        );
    }

    @Test
    void testCheckOverlappingDiscount() {
        LocalDateTime startDate = LocalDateTime.of(2025, 3, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2025, 3, 8, 23, 59, 59);

        when(discountRepository.checkOverlappingDiscount(4L, startDate, endDate)).thenReturn(2);

        int count = discountService.checkOverlappingDiscount(4L, startDate, endDate);

        assertEquals(2, count);
    }

}
