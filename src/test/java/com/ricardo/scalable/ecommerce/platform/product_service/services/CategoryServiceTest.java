package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
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

    @Autowired
    CategoryService categoryService;

    @Test
    void testFindByCategoryId() {
        when(categoryRepository.findByCategoryId(2L)).thenReturn(Data.createCategory001());
        when(categoryRepository.findByCategoryId(4L)).thenReturn(Data.createSubCategory001());

        Optional<Category> categoryOptional = categoryService.findByCategoryId(2L);
        Optional<Category> subCategoryOptional = categoryService.findByCategoryId(4L);

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(2L, categoryOptional.orElseThrow().getCategoryId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );

        assertAll(
            () -> assertTrue(subCategoryOptional.isPresent()),
            () -> assertEquals(4L, subCategoryOptional.orElseThrow().getCategoryId()),
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
            () -> assertEquals(2L, categoryOptional.orElseThrow().getCategoryId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );

        assertAll(
            () -> assertTrue(subCategoryOptional.isPresent()),
            () -> assertEquals(5L, subCategoryOptional.orElseThrow().getCategoryId()),
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
            () -> assertEquals(2L, categoryList.get(0).getCategoryId()),
            () -> assertEquals(3L, categoryList.get(1).getCategoryId()),
            () -> assertEquals(4L, categoryList.get(2).getCategoryId()),
            () -> assertEquals(5L, categoryList.get(3).getCategoryId()),
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
            () -> assertEquals(5L, categoryResponse.getCategoryId()),
            () -> assertEquals("Deportes", categoryResponse.getName()),
            () -> assertEquals("Descripcion deportes", categoryResponse.getDescription())
        );
    }

    @Test
    void testUpdate() {
        Category updateCategoryRequest = new Category();
        updateCategoryRequest.setCategoryId(5L);
        updateCategoryRequest.setName("Futbol");
        updateCategoryRequest.setDescription("Descripcion futbol");
        updateCategoryRequest.setParent(null);

        when(categoryRepository.findByCategoryId(5L)).thenReturn(Data.createCategory002());
        when(categoryRepository.save(any())).thenReturn(updateCategoryRequest);

        Optional<Category> updateCategoryResponse = categoryService.update(updateCategoryRequest, 3L);

        assertAll(
            () -> assertTrue(updateCategoryResponse.isPresent()),
            () -> assertEquals(5L, updateCategoryResponse.orElseThrow().getCategoryId()),
            () -> assertEquals("Futbol", updateCategoryResponse.orElseThrow().getName()),
            () -> assertEquals("Descripcion futbol", updateCategoryResponse.orElseThrow().getDescription())
        );
    }
}
