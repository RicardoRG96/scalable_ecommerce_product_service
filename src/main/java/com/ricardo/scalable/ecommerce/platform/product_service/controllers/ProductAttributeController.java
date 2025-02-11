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

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductAttributeService;

import jakarta.validation.Valid;

@RestController
public class ProductAttributeController {

    @Autowired
    private ProductAttributeService productAttributeService;

    @GetMapping("/product-attribute/{id}")
    public ResponseEntity<ProductAttribute> getById(@PathVariable Long id) {
        Optional<ProductAttribute> productAttributeOptional = productAttributeService.findById(id);
        boolean isPresent = productAttributeOptional.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(productAttributeOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-attribute/type/{type}")
    public ResponseEntity<List<ProductAttribute>> getByType(@PathVariable String type) {
        Optional<List<ProductAttribute>> productAttributeOptional = productAttributeService.findByType(type);
        boolean isPresent = productAttributeOptional.isPresent();
        boolean isEmpty = productAttributeOptional.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productAttributeOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-attribute/value/{value}")
    public ResponseEntity<ProductAttribute> getByValue(@PathVariable String value) {
        Optional<ProductAttribute> productAttributeOptional = productAttributeService.findByValue(value);
        boolean isPresent = productAttributeOptional.isPresent();

        if (isPresent) {
            return ResponseEntity.ok(productAttributeOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-attribute")
    public ResponseEntity<Iterable<ProductAttribute>> getAllProductAttributes() {
        return ResponseEntity.ok(productAttributeService.findAll());
    }

    @PostMapping("/product-attribute")
    public ResponseEntity<?> createProductAttribute(
        @Valid @RequestBody ProductAttributeCreationDto productAttribute,
        BindingResult result 
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        ProductAttribute createdProductAttribute = productAttributeService.save(productAttribute);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductAttribute);
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors()
                .forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/product-attribute/{id}")
    public ResponseEntity<?> updateProductAttribute(
        @Valid @RequestBody ProductAttribute productAttribute, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }

        Optional<ProductAttribute> productAttributeOptional = productAttributeService.update(productAttribute, id);

        if (productAttributeOptional.isPresent()) {
            return ResponseEntity.ok(productAttributeOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product-attribute/{id}")
    public ResponseEntity<?> deleteProductAttribute(@PathVariable Long id) {
        Optional<ProductAttribute> productAttributeOptional = productAttributeService.findById(id);

        if (productAttributeOptional.isPresent()) {
            productAttributeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
