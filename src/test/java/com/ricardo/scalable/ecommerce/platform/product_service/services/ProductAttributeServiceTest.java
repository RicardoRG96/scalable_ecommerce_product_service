package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;

@SpringBootTest
public class ProductAttributeServiceTest {

    @MockitoBean
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Test
    void testFindById() {
        when(productAttributeRepository.findById(1L)).thenReturn(createProductAttribute001());
        when(productAttributeRepository.findById(2L)).thenReturn(createProductAttribute002());

        Optional<ProductAttribute> productAttributeOptional1 = productAttributeService.findById(1L);
        Optional<ProductAttribute> productAttributeOptional2 = productAttributeService.findById(2L);

        assertAll(
            () -> assertTrue(productAttributeOptional1.isPresent()),
            () -> assertEquals(1L, productAttributeOptional1.orElseThrow().getId()),
            () -> assertEquals("color", productAttributeOptional1.orElseThrow().getType()),
            () -> assertEquals("rojo", productAttributeOptional1.orElseThrow().getValue())
        );

        assertAll(
            () -> assertTrue(productAttributeOptional2.isPresent()),
            () -> assertEquals(2L, productAttributeOptional2.orElseThrow().getId()),
            () -> assertEquals("color", productAttributeOptional2.orElseThrow().getType()),
            () -> assertEquals("azul", productAttributeOptional2.orElseThrow().getValue())
        );
    }

    @Test
    void testFindByType() {
        when(productAttributeRepository.findByType("color")).thenReturn(createListOfProductAttributesColorType());
        when(productAttributeRepository.findByType("size")).thenReturn(createListOfProductAttributesSizeType());

        Optional<List<ProductAttribute>> productAttributeOptionalList1 = 
            productAttributeService.findByType("color");

        Optional<List<ProductAttribute>> productAttributeOptionalList2 = 
            productAttributeService.findByType("size");

        assertAll(
            () -> assertTrue(productAttributeOptionalList1.isPresent()),
            () -> assertEquals(1L, productAttributeOptionalList1.orElseThrow().get(0).getId()),
            () -> assertEquals(2L, productAttributeOptionalList1.orElseThrow().get(1).getId()),
            () -> assertEquals(3L, productAttributeOptionalList1.orElseThrow().get(2).getId()),
            () -> assertEquals("color", productAttributeOptionalList1.orElseThrow().get(0).getType()),
            () -> assertEquals("color", productAttributeOptionalList1.orElseThrow().get(1).getType()),
            () -> assertEquals("color", productAttributeOptionalList1.orElseThrow().get(2).getType()),
            () -> assertEquals("rojo", productAttributeOptionalList1.orElseThrow().get(0).getValue()),
            () -> assertEquals("azul", productAttributeOptionalList1.orElseThrow().get(1).getValue()),
            () -> assertEquals("negro", productAttributeOptionalList1.orElseThrow().get(2).getValue())
        );

        assertAll(
            () -> assertTrue(productAttributeOptionalList2.isPresent()),
            () -> assertEquals(4L, productAttributeOptionalList2.orElseThrow().get(0).getId()),
            () -> assertEquals(5L, productAttributeOptionalList2.orElseThrow().get(1).getId()),
            () -> assertEquals(6L, productAttributeOptionalList2.orElseThrow().get(2).getId()),
            () -> assertEquals("size", productAttributeOptionalList2.orElseThrow().get(0).getType()),
            () -> assertEquals("size", productAttributeOptionalList2.orElseThrow().get(1).getType()),
            () -> assertEquals("size", productAttributeOptionalList2.orElseThrow().get(2).getType()),
            () -> assertEquals("S", productAttributeOptionalList2.orElseThrow().get(0).getValue()),
            () -> assertEquals("M", productAttributeOptionalList2.orElseThrow().get(1).getValue()),
            () -> assertEquals("L", productAttributeOptionalList2.orElseThrow().get(2).getValue())
        );
    }

    @Test
    void testFindByValue() {
        when(productAttributeRepository.findByValue("negro")).thenReturn(createProductAttribute003());
        when(productAttributeRepository.findByValue("S")).thenReturn(createProductAttribute004());

        Optional<ProductAttribute> productAttributeOptional1 = productAttributeService.findByValue("negro");
        Optional<ProductAttribute> productAttributeOptional2 = productAttributeService.findByValue("S");

        assertAll(
            () -> assertTrue(productAttributeOptional1.isPresent()),
            () -> assertEquals(3L, productAttributeOptional1.orElseThrow().getId()),
            () -> assertEquals("color", productAttributeOptional1.orElseThrow().getType()),
            () -> assertEquals("negro", productAttributeOptional1.orElseThrow().getValue())
        );

        assertAll(
            () -> assertTrue(productAttributeOptional2.isPresent()),
            () -> assertEquals(4L, productAttributeOptional2.orElseThrow().getId()),
            () -> assertEquals("size", productAttributeOptional2.orElseThrow().getType()),
            () -> assertEquals("S", productAttributeOptional2.orElseThrow().getValue())
        );
    }

    @Test
    void testFindAll() {
        when(productAttributeRepository.findAll()).thenReturn(createListOfProductAttributes());

        List<ProductAttribute> productAttributeList = (List<ProductAttribute>) productAttributeService.findAll();

        assertAll(
            () -> assertNotNull(productAttributeList),
            () -> assertEquals(7, productAttributeList.size()),
            () -> assertEquals(1L, productAttributeList.get(0).getId()),
            () -> assertEquals(2L, productAttributeList.get(1).getId()),
            () -> assertEquals(3L, productAttributeList.get(2).getId()),
            () -> assertEquals(4L, productAttributeList.get(3).getId()),
            () -> assertEquals(5L, productAttributeList.get(4).getId()),
            () -> assertEquals(6L, productAttributeList.get(5).getId()),
            () -> assertEquals("color", productAttributeList.get(0).getType()),
            () -> assertEquals("color", productAttributeList.get(1).getType()),
            () -> assertEquals("color", productAttributeList.get(2).getType()),
            () -> assertEquals("size", productAttributeList.get(3).getType()),
            () -> assertEquals("size", productAttributeList.get(4).getType()),
            () -> assertEquals("size", productAttributeList.get(5).getType()),
            () -> assertEquals("rojo", productAttributeList.get(0).getValue()),
            () -> assertEquals("azul", productAttributeList.get(1).getValue()),
            () -> assertEquals("negro", productAttributeList.get(2).getValue()),
            () -> assertEquals("S", productAttributeList.get(3).getValue()),
            () -> assertEquals("M", productAttributeList.get(4).getValue()),
            () -> assertEquals("L", productAttributeList.get(5).getValue())
        );
    }

    @Test
    void testSave() {
        ProductAttributeCreationDto productAttributeCreationRequest = new ProductAttributeCreationDto();
        productAttributeCreationRequest.setType("size");
        productAttributeCreationRequest.setValue("XL");

        ProductAttribute productAttributeCreationResponse = new ProductAttribute(
            7L, "size", "XL", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

        when(productAttributeRepository.save(any())).thenReturn(productAttributeCreationResponse);

        ProductAttribute productAttribute = productAttributeService.save(productAttributeCreationRequest);

        assertAll(
            () -> assertNotNull(productAttribute),
            () -> assertEquals(7L, productAttribute.getId()),
            () -> assertEquals("size", productAttribute.getType()),
            () -> assertEquals("XL", productAttribute.getValue())
        );
    }

    @Test
    void testUpdate() {
        ProductAttribute productAttributeUpdated = new ProductAttribute(
            2L, "color", "azul marino", Timestamp.from(Instant.now()), Timestamp.from(Instant.now()));

        when(productAttributeRepository.findById(2L)).thenReturn(createProductAttribute002());
        when(productAttributeRepository.save(any())).thenReturn(productAttributeUpdated);

        Optional<ProductAttribute> productAttributeOptional = 
            productAttributeService.update(productAttributeUpdated, 2L);

        assertAll(
            () -> assertTrue(productAttributeOptional.isPresent()),
            () -> assertEquals(2L, productAttributeOptional.orElseThrow().getId()),
            () -> assertEquals("color", productAttributeOptional.orElseThrow().getType()),
            () -> assertEquals("azul marino", productAttributeOptional.orElseThrow().getValue())
        );
    }

    @Test
    void testDelete() {
        doNothing().when(productAttributeRepository).deleteById(3L);

        productAttributeService.delete(3L);

        verify(productAttributeRepository, times(1)).deleteById(3L);
    }

}
