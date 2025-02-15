package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductGalleryService;

import jakarta.validation.Valid;

@RestController
public class ProductGalleryController {

    @Autowired
    private ProductGalleryService productGalleryService;

    @GetMapping("/product-gallery/{id}")
    public ResponseEntity<ProductGallery> getById(@PathVariable Long id) {
        Optional<ProductGallery> productGallery = productGalleryService.findById(id);
        boolean isPresent = productGallery.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(productGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-gallery/product/{id}")
    public ResponseEntity<List<ProductGallery>> getByProductId(@PathVariable Long id) {
        Optional<List<ProductGallery>> productGallery = productGalleryService.findByProductId(id);
        boolean isPresent = productGallery.isPresent();
        boolean isEmpty = productGallery.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-gallery/color-attribute/{id}")
    public ResponseEntity<List<ProductGallery>> getByColorAttributeId(@PathVariable Long id) {
        Optional<List<ProductGallery>> productGallery = productGalleryService.findByColorAttributeId(id);
        boolean isPresent = productGallery.isPresent();
        boolean isEmpty = productGallery.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-gallery/product/{productId}/color-attribute/{colorAttributeId}")
    public ResponseEntity<ProductGallery> getByProductIdAndColorAttributeId(
        @PathVariable Long productId, 
        @PathVariable Long colorAttributeId
    ) {
        Optional<ProductGallery> productGallery = productGalleryService.findByProductIdAndColorAttributeId(productId, colorAttributeId);
        boolean isPresent = productGallery.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(productGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-gallery")
    public ResponseEntity<Iterable<ProductGallery>> getAll() {
        List<ProductGallery> productGallery = (List<ProductGallery>) productGalleryService.findAll();
        return ResponseEntity.ok(productGallery);
    }

    @PostMapping("/product-gallery")
    public ResponseEntity<?> createProductGallery(
        @Valid @RequestBody ProductGalleryCreationDto productGallery,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<ProductGallery> savedProductGallery = productGalleryService.save(productGallery);
        boolean isPresent = savedProductGallery.isPresent();

        if (isPresent) {
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProductGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        
        result.getFieldErrors()
            .forEach(err -> {
                errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
            });
        
        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/product-gallery/{id}")
    public ResponseEntity<?> updateProductGallery(
        @Valid @RequestBody ProductGallery productGallery,
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<ProductGallery> updatedProductGallery = productGalleryService.update(productGallery, id);
        boolean isPresent = updatedProductGallery.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(updatedProductGallery.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product-gallery/{id}")
    public ResponseEntity<?> deleteProductGallery(@PathVariable Long id) {
        Optional<ProductGallery> productGallery = productGalleryService.findById(id);
        boolean isPresent = productGallery.isPresent();

        if (isPresent) {
            productGalleryService.delete(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
