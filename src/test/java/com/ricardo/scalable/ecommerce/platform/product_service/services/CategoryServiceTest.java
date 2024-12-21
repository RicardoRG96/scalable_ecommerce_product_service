package com.ricardo.scalable.ecommerce.platform.product_service.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.ricardo.scalable.ecommerce.platform.product_service.Data;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.CategoryCreationDto;

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
    void testFindById() {
        when(categoryRepository.findById(2L)).thenReturn(Data.createCategory001());

        Optional<Category> categoryOptional = categoryService.findById(2L);

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(2L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindByName() {
        when(categoryRepository.findByName("Tecnología")).thenReturn(Data.createCategory001());

        Optional<Category> categoryOptional = categoryService.findByName("Tecnología");

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(2L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología 2", categoryOptional.orElseThrow().getDescription())
        );
    }

    @Test
    void testFindAll() {
        when(categoryRepository.findAll()).thenReturn(Data.createListOfCategories());

        List<Category> categoryList = (List<Category>) categoryService.findAll();

        assertAll(
            () -> assertNotNull(categoryList),
            () -> assertEquals(2L, categoryList.get(0).getId()),
            () -> assertEquals(3L, categoryList.get(1).getId()),
            () -> assertEquals("Tecnología", categoryList.get(0).getName()),
            () -> assertEquals("Deportes", categoryList.get(1).getName()),
            () -> assertEquals(2, categoryList.size())
        );
    }

    @Test
    void testSave() {
        CategoryCreationDto categoryCreationRequest = new CategoryCreationDto();
        categoryCreationRequest.setName("Deportes");
        categoryCreationRequest.setDescription("Descripcion deportes");

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
        updateCategoryRequest.setId(3L);
        updateCategoryRequest.setName("Futbol");
        updateCategoryRequest.setDescription("Descripcion futbol");

        when(categoryRepository.findById(3L)).thenReturn(Data.createCategory002());
        when(categoryRepository.save(any())).thenReturn(updateCategoryRequest);

        Optional<Category> updateCategoryResponse = categoryService.update(updateCategoryRequest, 3L);

        assertAll(
            () -> assertTrue(updateCategoryResponse.isPresent()),
            () -> assertEquals(3L, updateCategoryResponse.orElseThrow().getId()),
            () -> assertEquals("Futbol", updateCategoryResponse.orElseThrow().getName()),
            () -> assertEquals("Descripcion futbol", updateCategoryResponse.orElseThrow().getDescription())
        );
    }
}
