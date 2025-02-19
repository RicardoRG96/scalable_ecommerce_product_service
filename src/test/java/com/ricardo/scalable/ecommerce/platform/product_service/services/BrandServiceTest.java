package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.BrandRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.BrandServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

@SpringBootTest
public class BrandServiceTest {

    @MockitoBean
    BrandRepository brandRepository;

    @MockitoBean
    ProductRepository productRepository;

    @Autowired
    BrandService brandService;

    @Test
    void testFindById() {
        when(brandRepository.findById(2L)).thenReturn(createBrand001());

        Optional<Brand> brandOptional = brandService.findById(2L);

        assertAll(
            () -> assertTrue(brandOptional.isPresent()),
            () -> assertEquals(1L, brandOptional.orElseThrow().getId()),
            () -> assertEquals("Apple", brandOptional.orElseThrow().getName()),
            () -> assertEquals("Marca Apple", brandOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindByName() {
        when(brandRepository.findByName("Apple")).thenReturn(createBrand001());

        Optional<Brand> brandOptional = brandService.findByName("Apple");

        assertAll(
            () -> assertTrue(brandOptional.isPresent()),
            () -> assertEquals(1L, brandOptional.orElseThrow().getId()),
            () -> assertEquals("Apple", brandOptional.orElseThrow().getName()),
            () -> assertEquals("Marca Apple", brandOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindAll() {
        when(brandRepository.findAll()).thenReturn(createListOfBrands());

        List<Brand> brandList = (List<Brand>) brandService.findAll();

        assertAll(
            () -> assertNotNull(brandList),
            () -> assertFalse(brandList.isEmpty()),
            () -> assertEquals(2, brandList.size()),
            () -> assertEquals(1L, brandList.get(0).getId()),
            () -> assertEquals(2L, brandList.get(1).getId()),
            () -> assertEquals("Apple", brandList.get(0).getName()),
            () -> assertEquals("Samsung", brandList.get(1).getName()),
            () -> assertEquals("Marca Apple", brandList.get(0).getDescription()),
            () -> assertEquals("Marca Samsung", brandList.get(1).getDescription())
        );
    }

    @Test
    void testSave() {
        BrandCreationDto brandCreationRequest = createBrandCreationDto();
        Brand brandCreationResponse = createBrandCreationResponse();

        when(brandRepository.save(any())).thenReturn(brandCreationResponse);

        Brand createdBrand = brandService.save(brandCreationRequest);

        assertAll(
            () -> assertNotNull(createdBrand),
            () -> assertEquals(8L, createdBrand.getId()),
            () -> assertEquals("Nike", createdBrand.getName()),
            () -> assertEquals("Marca nike", createdBrand.getDescription())
        );
    }

    @Test
    void testUpdate() {
        Brand brandUpdateRequest = new Brand();
        brandUpdateRequest.setId(3L);
        brandUpdateRequest.setName("Apple");
        brandUpdateRequest.setDescription("Marca Apple nueva");

        when(brandRepository.findById(3L)).thenReturn(createBrand002());
        when(brandRepository.save(any())).thenReturn(brandUpdateRequest);

        Optional<Brand> brandOptionalResponse = brandService.update(brandUpdateRequest, 3L);

        assertAll(
            () -> assertTrue(brandOptionalResponse.isPresent()),
            () -> assertEquals(3L, brandOptionalResponse.orElseThrow().getId()),
            () -> assertEquals("Apple", brandOptionalResponse.orElseThrow().getName()),
            () -> assertEquals("Marca Apple nueva", brandOptionalResponse.orElseThrow().getDescription())
        );
    }

    @Test
    void testDelete() {
        Optional<List<Product>> productsWithSameBrand = Optional.of(
            List.of(
                createProduct002().orElseThrow(),
                createProduct003().orElseThrow()
            )
        );

        when(brandRepository.findByName("Unknown")).thenReturn(createUnknownBrand());
        when(productRepository.findByBrandId(2L)).thenReturn(productsWithSameBrand);

        brandService.delete(2L);

        verify(productRepository, times(2)).save(any());
        verify(brandRepository, times(1)).deleteById(2L);
    }
    
}
