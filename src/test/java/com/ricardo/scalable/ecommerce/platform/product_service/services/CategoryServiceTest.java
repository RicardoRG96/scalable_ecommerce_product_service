package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.unitTestData.Data;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {

    @MockitoBean
    CategoryRepository categoryRepository;

    @MockitoBean
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;

    @Test
    void testFindByCategoryId() {
        when(categoryRepository.findById(2L)).thenReturn(Data.createCategory001());
        when(categoryRepository.findById(4L)).thenReturn(Data.createSubCategory001());

        Optional<Category> categoryOptional = categoryService.findById(2L);
        Optional<Category> subCategoryOptional = categoryService.findById(4L);

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(2L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );

        assertAll(
            () -> assertTrue(subCategoryOptional.isPresent()),
            () -> assertEquals(4L, subCategoryOptional.orElseThrow().getId()),
            () -> assertEquals("Computadoras", subCategoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion computadoras", subCategoryOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindByName() {
        when(categoryRepository.findByName("Tecnología")).thenReturn(Data.createCategory001());
        when(categoryRepository.findByName("Celulares")).thenReturn(Data.createSubCategory002());

        Optional<Category> categoryOptional = categoryService.findByName("Tecnología");
        Optional<Category> subCategoryOptional = categoryService.findByName("Celulares");

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(2L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );

        assertAll(
            () -> assertTrue(subCategoryOptional.isPresent()),
            () -> assertEquals(5L, subCategoryOptional.orElseThrow().getId()),
            () -> assertEquals("Celulares", subCategoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion celulares", subCategoryOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(Data.createListOfCategories());

        List<Category> categoryList = (List<Category>) categoryService.findAll();

        assertAll(
            () -> assertNotNull(categoryList),
            () -> assertEquals(4, categoryList.size()),
            () -> assertEquals(2L, categoryList.get(0).getId()),
            () -> assertEquals(3L, categoryList.get(1).getId()),
            () -> assertEquals(4L, categoryList.get(2).getId()),
            () -> assertEquals(5L, categoryList.get(3).getId()),
            () -> assertEquals("Tecnología", categoryList.get(0).getName()),
            () -> assertEquals("Deportes", categoryList.get(1).getName()),
            () -> assertEquals("Computadoras", categoryList.get(2).getName()),
            () -> assertEquals("Celulares", categoryList.get(3).getName())
        );
    }

    @Test
    void testSave() {
        CategoryCreationDto categoryCreationRequest = new CategoryCreationDto();
        categoryCreationRequest.setName("Deportes");
        categoryCreationRequest.setDescription("Descripcion deportes");
        categoryCreationRequest.setParentId(null);

        when(categoryRepository.save(any())).thenReturn(Data.createCategory002().orElseThrow());

        Category categoryResponse = categoryService.save(categoryCreationRequest);

        assertAll(
            () -> assertNotNull(categoryResponse),
            () -> assertEquals(3L, categoryResponse.getId()),
            () -> assertEquals("Deportes", categoryResponse.getName()),
            () -> assertEquals("Descripcion deportes", categoryResponse.getDescription())
        );
    }

    @Test
    void testUpdate() {
        Category updateCategoryRequest = new Category();
        updateCategoryRequest.setId(5L);
        updateCategoryRequest.setName("Futbol");
        updateCategoryRequest.setDescription("Descripcion futbol");
        updateCategoryRequest.setParent(null);

        when(categoryRepository.findById(3L)).thenReturn(Data.createCategory002());
        when(categoryRepository.save(any())).thenReturn(updateCategoryRequest);

        Optional<Category> updateCategoryResponse = categoryService.update(updateCategoryRequest, 3L);

        assertAll(
            () -> assertTrue(updateCategoryResponse.isPresent()),
            () -> assertEquals(5L, updateCategoryResponse.orElseThrow().getId()),
            () -> assertEquals("Futbol", updateCategoryResponse.orElseThrow().getName()),
            () -> assertEquals("Descripcion futbol", updateCategoryResponse.orElseThrow().getDescription())
        );
    }

    @Test
    void testDelete() {
        Optional<List<Product>> productsWithSameCategory = Optional.of(
            List.of(
                Data.createProduct001().orElseThrow(),
                Data.createProduct003().orElseThrow()
            )
        );

        when(categoryRepository.findByName("Unknown")).thenReturn(Data.createUnknownCategory());
        when(productRepository.findByCategoryId(3L)).thenReturn(productsWithSameCategory);

        doNothing().when(categoryRepository).deleteById(3L);

        categoryService.delete(3L);

        verify(productRepository, times(2)).save(any());
        verify(categoryRepository, times(1)).deleteById(3L);
    }

}
