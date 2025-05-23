package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.DiscountType;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.DiscountRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.DiscountServiceImplTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.*;

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
            () -> assertEquals("PERCENTAGE", discount.orElseThrow().getDiscountType().name()),
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
            () -> assertEquals("AMOUNT", discount.orElseThrow().get(0).getDiscountType().name()),
            () -> assertEquals("PERCENTAGE", discount.orElseThrow().get(1).getDiscountType().name()),
            () -> assertEquals("PERCENTAGE", discount.orElseThrow().get(2).getDiscountType().name())
        );
    }

    @Test
    void testFindByDiscountType() {
        DiscountType discountType = DiscountType.PERCENTAGE;
        when(discountRepository.findByDiscountType(discountType)).thenReturn(createListOfDiscountByType());

        Optional<List<Discount>> discount = discountService.findByDiscountType(discountType);

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
            () -> assertEquals("PERCENTAGE", discount.orElseThrow().get(0).getDiscountType().name()),
            () -> assertEquals("AMOUNT", discount.orElseThrow().get(1).getDiscountType().name())
        );
    }

    @Test
    void testVerifyValidityPeriod() {
        when(discountRepository.verifyValidityPeriod(1L)).thenReturn(createDiscount001());

        Optional<Discount> discount = discountService.verifyValidityPeriod(1L);

        assertAll(
            () -> assertTrue(discount.isPresent()),
            () -> assertEquals(1L, discount.orElseThrow().getId()),
            () -> assertEquals("PERCENTAGE", discount.orElseThrow().getDiscountType().name()),
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

    @Test
    void testFindAll() {
        when(discountRepository.findAll()).thenReturn(createListOfDiscounts());

        List<Discount> discounts = discountService.findAll();

        assertAll(
            () -> assertEquals(5, discounts.size()),
            () -> assertEquals(1L, discounts.get(0).getId()),
            () -> assertEquals(2L, discounts.get(1).getId()),
            () -> assertEquals(3L, discounts.get(2).getId()),
            () -> assertEquals("PERCENTAGE", discounts.get(0).getDiscountType().name()),
            () -> assertEquals("AMOUNT", discounts.get(1).getDiscountType().name()),
            () -> assertEquals("AMOUNT", discounts.get(2).getDiscountType().name())
        );
    }

    @Test
    void testSave() {
        DiscountDto discountDto = createDiscountDtoCreation();
        Discount discount = createDiscountCreationResponse();

        when(productSkuRepository.findById(6L)).thenReturn(createProductSku006());
        when(productSkuRepository.findById(7L)).thenReturn(createProductSku007());
        when(discountRepository.save(any())).thenReturn(discount);

        Optional<Discount> newDiscount = discountService.save(discountDto);

        assertAll(
            () -> assertTrue(newDiscount.isPresent()),
            () -> assertEquals(6L, newDiscount.orElseThrow().getId()),
            () -> assertEquals("AMOUNT", newDiscount.orElseThrow().getDiscountType().name()),
            () -> assertEquals(12.0, newDiscount.orElseThrow().getDiscountValue()),
            () -> assertEquals("2025-03-10 00:00:00.0", newDiscount.orElseThrow().getStartDate().toString()),
            () -> assertEquals("2025-04-02 23:59:59.0", newDiscount.orElseThrow().getEndDate().toString()),
            () -> assertTrue(newDiscount.orElseThrow().getIsActive()),
            () -> assertEquals(2, newDiscount.orElseThrow().getProductSkus().size())
        );
    }

    @Test
    void testUpdate() {
        DiscountDto discountDto = createDiscountDtoUpdate();
        Discount discount = createDiscountUpdatedResponse();

        when(discountRepository.findById(5L)).thenReturn(createDiscount005());
        when(productSkuRepository.findById(3L)).thenReturn(createProductSku003());
        when(productSkuRepository.findById(4L)).thenReturn(createProductSku004());
        when(discountRepository.save(any())).thenReturn(discount);

        Optional<Discount> updatedDiscount = discountService.update(discountDto, 5L);

        assertAll(
            () -> assertTrue(updatedDiscount.isPresent()),
            () -> assertEquals(5L, updatedDiscount.orElseThrow().getId()),
            () -> assertEquals("PERCENTAGE", updatedDiscount.orElseThrow().getDiscountType().name()),
            () -> assertEquals(25.0, updatedDiscount.orElseThrow().getDiscountValue()),
            () -> assertEquals("2025-03-10 00:00:00.0", updatedDiscount.orElseThrow().getStartDate().toString()),
            () -> assertEquals("2025-04-12 23:59:59.0", updatedDiscount.orElseThrow().getEndDate().toString()),
            () -> assertTrue(updatedDiscount.orElseThrow().getIsActive()),
            () -> assertEquals(2, updatedDiscount.orElseThrow().getProductSkus().size())
        );
    }

    @Test
    void testDelete() {
        doNothing().when(discountRepository).deleteById(5L);

        discountService.delete(5L);

        verify(discountRepository, times(1)).deleteById(5L);
    }

}
