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
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.createDiscount004;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.createDiscount005;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountCodeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;

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

    @Test
    void testFindByCodeAndDiscountId() {
        when(discountCodeRepository.findByCodeAndDiscountId("10OFFMARZO", 1L)).thenReturn(createDiscountCode005());

        Optional<DiscountCode> discountCode = discountCodeService.findByCodeAndDiscountId("10OFFMARZO", 1L);

        assertAll(
            () -> assertTrue(discountCode.isPresent()),
            () -> assertEquals(5L, discountCode.orElseThrow().getId()),
            () -> assertEquals("10OFFMARZO", discountCode.orElseThrow().getCode()),
            () -> assertEquals("percentage", discountCode.orElseThrow().getDiscount().getDiscountType()),
            () -> assertEquals(100, discountCode.orElseThrow().getUsageLimit()),
            () -> assertEquals(0, discountCode.orElseThrow().getUsedCount())
        );
    }

    @Test
    void testFindByUsageLimit() {
        when(discountCodeRepository.findByUsageLimit(70)).thenReturn(createListOfDiscountCodeByUsageLimit());

        Optional<List<DiscountCode>> discountCodes = discountCodeService.findByUsageLimit(70);

        assertAll(
            () -> assertTrue(discountCodes.isPresent()),
            () -> assertFalse(discountCodes.orElseThrow().isEmpty()),
            () -> assertEquals(2L, discountCodes.orElseThrow().get(0).getId()),
            () -> assertEquals(4L, discountCodes.orElseThrow().get(1).getId()),
            () -> assertEquals("SUMMER2025", discountCodes.orElseThrow().get(0).getCode()),
            () -> assertEquals("LOYALTY2025", discountCodes.orElseThrow().get(1).getCode()),
            () -> assertEquals("fixed amount", discountCodes.orElseThrow().get(0).getDiscount().getDiscountType()),
            () -> assertEquals("quantity discount", discountCodes.orElseThrow().get(1).getDiscount().getDiscountType()),
            () -> assertEquals(70, discountCodes.orElseThrow().get(0).getUsageLimit()),
            () -> assertEquals(70, discountCodes.orElseThrow().get(1).getUsageLimit()),
            () -> assertEquals(10, discountCodes.orElseThrow().get(0).getUsedCount()),
            () -> assertEquals(0, discountCodes.orElseThrow().get(1).getUsedCount())
        );
    }

    @Test
    void testFindByUsedCount() {
        when(discountCodeRepository.findByUsedCount(10)).thenReturn(createListOfDiscountCodeByUsedCount());

        Optional<List<DiscountCode>> discountCodes = discountCodeService.findByUsedCount(10);

        assertAll(
            () -> assertTrue(discountCodes.isPresent()),
            () -> assertFalse(discountCodes.orElseThrow().isEmpty()),
            () -> assertEquals(1L, discountCodes.orElseThrow().get(0).getId()),
            () -> assertEquals(2L, discountCodes.orElseThrow().get(1).getId()),
            () -> assertEquals("MATEO2025", discountCodes.orElseThrow().get(0).getCode()),
            () -> assertEquals("SUMMER2025", discountCodes.orElseThrow().get(1).getCode()),
            () -> assertEquals("percentage", discountCodes.orElseThrow().get(0).getDiscount().getDiscountType()),
            () -> assertEquals("fixed amount", discountCodes.orElseThrow().get(1).getDiscount().getDiscountType()),
            () -> assertEquals(50, discountCodes.orElseThrow().get(0).getUsageLimit()),
            () -> assertEquals(70, discountCodes.orElseThrow().get(1).getUsageLimit()),
            () -> assertEquals(10, discountCodes.orElseThrow().get(0).getUsedCount()),
            () -> assertEquals(10, discountCodes.orElseThrow().get(1).getUsedCount())
        );
    }

    @Test
    void testFindAll() {
        when(discountCodeRepository.findAll()).thenReturn(createListOfDiscountCodes());

        List<DiscountCode> discountCodes = discountCodeService.findAll();

        assertAll(
            () -> assertFalse(discountCodes.isEmpty()),
            () -> assertEquals(5, discountCodes.size()),
            () -> assertEquals(1L, discountCodes.get(0).getId()),
            () -> assertEquals(2L, discountCodes.get(1).getId()),
            () -> assertEquals(3L, discountCodes.get(2).getId()),
            () -> assertEquals(4L, discountCodes.get(3).getId()),
            () -> assertEquals(5L, discountCodes.get(4).getId())
        );
    }

    @Test
    void testSave() {
        when(discountRepository.findById(5L)).thenReturn(createDiscount005());
        when(discountCodeRepository.save(any())).thenReturn(createDiscountCodeCreationResponse());

        DiscountCodeDto discountCodeCreationRequest = createDiscountCodeDtoCreation();
        Optional<DiscountCode> createdDiscountCode = discountCodeService.save(discountCodeCreationRequest);

        assertAll(
            () -> assertTrue(createdDiscountCode.isPresent()),
            () -> assertEquals(6L, createdDiscountCode.orElseThrow().getId()),
            () -> assertEquals("20OFFMARZO2025", createdDiscountCode.orElseThrow().getCode()),
            () -> assertEquals("percentage", createdDiscountCode.orElseThrow().getDiscount().getDiscountType()),
            () -> assertEquals(100, createdDiscountCode.orElseThrow().getUsageLimit()),
            () -> assertEquals(0, createdDiscountCode.orElseThrow().getUsedCount())
        );
    }

    @Test
    void testUpdate() {
        when(discountCodeRepository.findById(5L)).thenReturn(createDiscountCode005());
        when(discountRepository.findById(5L)).thenReturn(createDiscount005());
        when(discountCodeRepository.save(any())).thenReturn(createDiscountCodeUpdateResponse());

        DiscountCodeDto discountCodeUpdateRequest = createDiscountCodeDtoUpdate();
        Optional<DiscountCode> updatedDiscountCode = discountCodeService.update(discountCodeUpdateRequest, 5L);

        assertAll(
            () -> assertTrue(updatedDiscountCode.isPresent()),
            () -> assertEquals(5L, updatedDiscountCode.orElseThrow().getId()),
            () -> assertEquals("20OFFMARZO-ABRIL2025", updatedDiscountCode.orElseThrow().getCode()),
            () -> assertEquals("percentage", updatedDiscountCode.orElseThrow().getDiscount().getDiscountType()),
            () -> assertEquals(120, updatedDiscountCode.orElseThrow().getUsageLimit()),
            () -> assertEquals(0, updatedDiscountCode.orElseThrow().getUsedCount())
        );
    }

}
