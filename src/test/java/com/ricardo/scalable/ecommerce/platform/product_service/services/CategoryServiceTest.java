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

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.CategoryServiceTestData.*;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductServiceTestData.*;

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
    void testFindById() {
        when(categoryRepository.findById(1L)).thenReturn(createParentCategory001());
        when(categoryRepository.findById(4L)).thenReturn(createSubCategory001());

        Optional<Category> categoryOptional = categoryService.findById(1L);
        Optional<Category> subCategoryOptional = categoryService.findById(4L);

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(1L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología", categoryOptional.orElseThrow().getDescription())
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
        when(categoryRepository.findByName("Tecnología")).thenReturn(createParentCategory001());
        when(categoryRepository.findByName("Celulares")).thenReturn(createSubCategory002());

        Optional<Category> categoryOptional = categoryService.findByName("Tecnología");
        Optional<Category> subCategoryOptional = categoryService.findByName("Celulares");

        assertAll(
            () -> assertTrue(categoryOptional.isPresent()),
            () -> assertEquals(1L, categoryOptional.orElseThrow().getId()),
            () -> assertEquals("Tecnología", categoryOptional.orElseThrow().getName()),
            () -> assertEquals("Descripcion tecnología", categoryOptional.orElseThrow().getDescription())
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
        when(categoryRepository.findAll()).thenReturn(createListOfCategories());

        List<Category> categoryList = (List<Category>) categoryService.findAll();

        assertAll(
            () -> assertNotNull(categoryList),
            () -> assertEquals(8, categoryList.size()),
            () -> assertEquals(1L, categoryList.get(0).getId()),
            () -> assertEquals(2L, categoryList.get(1).getId()),
            () -> assertEquals(3L, categoryList.get(2).getId()),
            () -> assertEquals(4L, categoryList.get(3).getId()),
            () -> assertEquals("Tecnología", categoryList.get(0).getName()),
            () -> assertEquals("Deportes", categoryList.get(1).getName()),
            () -> assertEquals("Ropa Hombre", categoryList.get(2).getName()),
            () -> assertEquals("Computadoras", categoryList.get(3).getName())
        );
    }

    @Test
    void testSave() {
        CategoryCreationDto categoryCreationRequest = createCategoryCreationDto();
        Category categoryCreationResponse = createCategoryCreationResponse();

        when(categoryRepository.save(any())).thenReturn(categoryCreationResponse);

        Category createdCategory = categoryService.save(categoryCreationRequest);

        assertAll(
            () -> assertNotNull(createdCategory),
            () -> assertEquals(9L, createdCategory.getId()),
            () -> assertEquals("Electrohogar", createdCategory.getName()),
            () -> assertEquals("Descripcion electrohogar", createdCategory.getDescription())
        );
    }

    @Test
    void testUpdate() {
        Category updateCategoryRequest = new Category();
        updateCategoryRequest.setId(5L);
        updateCategoryRequest.setName("Futbol");
        updateCategoryRequest.setDescription("Descripcion futbol");
        updateCategoryRequest.setParent(null);

        when(categoryRepository.findById(3L)).thenReturn(createParentCategory002());
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
                createProduct001().orElseThrow(),
                createProduct003().orElseThrow()
            )
        );

        when(categoryRepository.findByName("Unknown")).thenReturn(createUnknownCategory());
        when(productRepository.findByCategoryId(3L)).thenReturn(productsWithSameCategory);

        doNothing().when(categoryRepository).deleteById(3L);

        categoryService.delete(3L);

        verify(productRepository, times(2)).save(any());
        verify(categoryRepository, times(1)).deleteById(3L);
    }

}
