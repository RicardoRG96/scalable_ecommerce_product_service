package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

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

    @Test
    void testFindByCode() {
        when(discountCodeRepository.findByCode("SUMMER2025")).thenReturn(createDiscountCode002());

        Optional<DiscountCode> discountCode = discountCodeService.findByCode("SUMMER2025");

        assertAll(
            () -> assertTrue(discountCode.isPresent()),
            () -> assertEquals(2L, discountCode.orElseThrow().getId()),
            () -> assertEquals("SUMMER2025", discountCode.orElseThrow().getCode()),
            () -> assertEquals("fixed amount", discountCode.orElseThrow().getDiscount().getDiscountType()),
            () -> assertEquals(70, discountCode.orElseThrow().getUsageLimit()),
            () -> assertEquals(10, discountCode.orElseThrow().getUsedCount())
        );
    }

    @Test
    void testFindByDiscountId() {
        when(discountCodeRepository.findByDiscountId(1L)).thenReturn(createListOfDiscountCodeByDiscountId1());

        Optional<List<DiscountCode>> discountCodes = discountCodeService.findByDiscountId(1L);

        assertAll(
            () -> assertTrue(discountCodes.isPresent()),
            () -> assertFalse(discountCodes.orElseThrow().isEmpty()),
            () -> assertEquals(1L, discountCodes.orElseThrow().get(0).getId()),
            () -> assertEquals(5L, discountCodes.orElseThrow().get(1).getId()),
            () -> assertEquals("MATEO2025", discountCodes.orElseThrow().get(0).getCode()),
            () -> assertEquals("10OFFMARZO", discountCodes.orElseThrow().get(1).getCode()),
            () -> assertEquals("percentage", discountCodes.orElseThrow().get(0).getDiscount().getDiscountType()),
            () -> assertEquals("percentage", discountCodes.orElseThrow().get(1).getDiscount().getDiscountType()),
            () -> assertEquals(50, discountCodes.orElseThrow().get(0).getUsageLimit()),
            () -> assertEquals(100, discountCodes.orElseThrow().get(1).getUsageLimit()),
            () -> assertEquals(10, discountCodes.orElseThrow().get(0).getUsedCount()),
            () -> assertEquals(0, discountCodes.orElseThrow().get(1).getUsedCount())
        );
    }

}
