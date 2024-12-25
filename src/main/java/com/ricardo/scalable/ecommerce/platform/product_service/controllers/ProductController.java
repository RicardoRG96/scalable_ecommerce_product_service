package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductService;

import jakarta.validation.Valid;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getByProductId(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-name/{name}")
    public ResponseEntity<Product> getByName(@PathVariable String name) {
        Optional<Product> productOptional = productService.findByName(name);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/{sku}")
    public ResponseEntity<Product> getBySku(@PathVariable String sku) {
        Optional<Product> productOptional = productService.findBySku(sku);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    // @PostMapping
    // public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreationDto product, BindingResult result) {
    //     if (result.hasErrors()) {
    //         return this.validation(result);
    //     }

    //     return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
    // }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreationDto product, BindingResult result) {
        try {
            if (result.hasErrors()) {
                return this.validation(result);
            }
    
            return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(product));
        } catch (DataIntegrityViolationException ex) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", ex.getMessage());
            errorResponse.put("name", ex.getCause().toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors()
                .forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
        @Valid @RequestBody Product product, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }

        Optional<Product> productOptional = productService.update(product, id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> productOptional = productService.findById(id);

        if (productOptional.isPresent()) {
            productService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
