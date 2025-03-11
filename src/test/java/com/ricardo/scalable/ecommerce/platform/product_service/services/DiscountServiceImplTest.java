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

}
