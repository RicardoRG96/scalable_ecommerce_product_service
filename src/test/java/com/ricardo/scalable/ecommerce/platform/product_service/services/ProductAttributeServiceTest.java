package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductAttributeServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.*;

@SpringBootTest
public class ProductAttributeServiceTest {

    @MockitoBean
    private ProductAttributeRepository productAttributeRepository;

    @MockitoBean
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private ProductAttributeService productAttributeService;

    @Test
    void testFindById() {
        when(productAttributeRepository.findById(1L)).thenReturn(createProductAttribute001());

        Optional<ProductAttribute> productAttributeOptional1 = productAttributeService.findById(1L);

        assertAll(
            () -> assertTrue(productAttributeOptional1.isPresent()),
            () -> assertEquals(1L, productAttributeOptional1.orElseThrow().getId()),
            () -> assertEquals("color", productAttributeOptional1.orElseThrow().getType()),
            () -> assertEquals("rojo", productAttributeOptional1.orElseThrow().getValue())
        );
    }

    @Test
    void testFindByType() {
        when(productAttributeRepository.findByType("color")).thenReturn(createListOfProductAttributesColorType());

        Optional<List<ProductAttribute>> productAttributeOptionalList1 = 
            productAttributeService.findByType("color");

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
    }

    @Test
    void testFindByValue() {
        when(productAttributeRepository.findByValue("S")).thenReturn(createProductAttribute004());

        Optional<ProductAttribute> productAttributeOptional2 = productAttributeService.findByValue("S");

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
            () -> assertEquals(8, productAttributeList.size()),
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
        ProductAttributeCreationDto productAttributeCreationRequest = createProductAttributeCreationDto();
        ProductAttribute productAttributeCreationResponse = createProductAttributeCreationResponse();

        when(productAttributeRepository.save(any())).thenReturn(productAttributeCreationResponse);

        ProductAttribute productAttribute = productAttributeService.save(productAttributeCreationRequest);

        assertAll(
            () -> assertNotNull(productAttribute),
            () -> assertEquals(9L, productAttribute.getId()),
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
    void testDeleteColorAttribute() {
        when(productAttributeRepository.findById(3L)).thenReturn(createProductAttribute003());
        when(productAttributeRepository.findByValue("none-color")).thenReturn(createProductAttribute008());
        when(productSkuRepository.findByColorAttributeId(3L)).thenReturn(createListOfProductSkuByColorAttributeId());

        doNothing().when(productAttributeRepository).deleteById(3L);

        productAttributeService.delete(3L);

        verify(productAttributeRepository, times(1)).deleteById(3L);
    }

    @Test
    void testDeleteSizeAttribute() {
        when(productAttributeRepository.findById(4L)).thenReturn(createProductAttribute004());
        when(productAttributeRepository.findByValue("none-size")).thenReturn(createProductAttribute007());
        when(productSkuRepository.findBySizeAttributeId(4L)).thenReturn(createListOfProductSkuBySizeAttributeId());
        
        doNothing().when(productAttributeRepository).deleteById(4L);

        productAttributeService.delete(4L);

        verify(productAttributeRepository, times(1)).deleteById(4L);
    }

}
