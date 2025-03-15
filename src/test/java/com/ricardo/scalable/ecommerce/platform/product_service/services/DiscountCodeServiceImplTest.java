package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountCodeServiceImplTestData.*;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountCodeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;

@SpringBootTest
public class DiscountCodeServiceImplTest {

    @MockitoBean
    private DiscountCodeRepository discountCodeRepository;

    @MockitoBean
    private DiscountRepository discountRepository;
    
    @Autowired
    private DiscountCodeService discountCodeService;

    @Test
    void testFindById() {
        when(discountCodeRepository.findById(1L)).thenReturn(createDiscountCode001());

        Optional<DiscountCode> discountCode = discountCodeService.findById(1L);

        assertAll(
            () -> assertTrue(discountCode.isPresent()),
            () -> assertEquals(1L, discountCode.orElseThrow().getId()),
            () -> assertEquals("MATEO2025", discountCode.orElseThrow().getCode()),
            () -> assertEquals("percentage", discountCode.orElseThrow().getDiscount().getDiscountType()),
            () -> assertEquals(50, discountCode.orElseThrow().getUsageLimit()),
            () -> assertEquals(10, discountCode.orElseThrow().getUsedCount())
        );
    }

}
