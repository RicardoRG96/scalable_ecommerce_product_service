package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.util.HashMap;
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

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductSkuService;

import jakarta.validation.Valid;

@RestController
public class ProductSkuController {

    @Autowired
    private ProductSkuService productSkuService;

    @GetMapping("/product-sku/{id}")
    public ResponseEntity<ProductSku> getByProductSkuId(@PathVariable Long id) {
        Optional<ProductSku> productSkuOptional = productSkuService.findById(id);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/product/{productId}")
    public ResponseEntity<Iterable<ProductSku>> getByProductId(@PathVariable Long productId) {
        Optional<Iterable<ProductSku>>productsSku = productSkuService.findByProductId(productId);
        boolean isPresent = productsSku.isPresent();
        boolean isEmpty = productsSku.orElseThrow().iterator().hasNext();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productsSku.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/sku/{sku}")
    public ResponseEntity<ProductSku> getBySku(@PathVariable String sku) {
        Optional<ProductSku> productSkuOptional = productSkuService.findBySku(sku);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/sku/{sku}/isActive/{isActive}")
    public ResponseEntity<ProductSku> getBySkuAndIsActive(@PathVariable String sku, @PathVariable Boolean isActive) {
        Optional<ProductSku> productSkuOptional = productSkuService.findBySkuAndIsActive(sku, isActive);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/sizeAttributeId/{sizeAttributeId}")
    public ResponseEntity<Iterable<ProductSku>> getBySizeAttributeId(@PathVariable Long sizeAttributeId) {
        Optional<Iterable<ProductSku>> productsSkuOptional = productSkuService.findBySizeAttributeId(sizeAttributeId);
        boolean isPresent = productsSkuOptional.isPresent();
        boolean isEmpty = productsSkuOptional.orElseThrow().iterator().hasNext();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productsSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/colorAttributeId/{colorAttributeId}")
    public ResponseEntity<Iterable<ProductSku>> getByColorAttributeId(@PathVariable Long colorAttributeId) {
        Optional<Iterable<ProductSku>> productsSku = productSkuService.findByColorAttributeId(colorAttributeId);
        boolean isPresent = productsSku.isPresent();
        boolean isEmpty = productsSku.orElseThrow().iterator().hasNext();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productsSku.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/productId/{productId}/sizeAttributeId/{sizeAttributeId}")
    public ResponseEntity<Iterable<ProductSku>> getByProductIdAndSizeAttributeId(
            @PathVariable Long productId, 
            @PathVariable Long sizeAttributeId
        ) {
        Optional<Iterable<ProductSku>> productsSku = 
            productSkuService.findByProductIdAndSizeAttributeId(productId, sizeAttributeId);

        boolean isPresent = productsSku.isPresent();
        boolean isEmpty = productsSku.orElseThrow().iterator().hasNext();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productsSku.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/productId/{productId}/colorAttributeId/{colorAttributeId}")
    public ResponseEntity<Iterable<ProductSku>> getByProductIdAndColorAttributeId(
            @PathVariable Long productId, 
            @PathVariable Long colorAttributeId
        ) {
        Optional<Iterable<ProductSku>> productsSku = 
            productSkuService.findByProductIdAndColorAttributeId(productId, colorAttributeId);

        boolean isPresent = productsSku.isPresent();
        boolean isEmpty = productsSku.orElseThrow().iterator().hasNext();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productsSku.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/productId/{productId}/sizeAttributeId/{sizeAttributeId}/colorAttributeId/{colorAttributeId}")
    public ResponseEntity<ProductSku> getByProductIdAndSizeAttributeIdAndColorAttributeId(
            @PathVariable Long productId, 
            @PathVariable Long sizeAttributeId, 
            @PathVariable Long colorAttributeId
        ) {
        Optional<ProductSku> productSkuOptional = 
            productSkuService.findByProductIdAndSizeAttributeIdAndColorAttributeId(
                productId, sizeAttributeId, colorAttributeId
            );

        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku")
    public ResponseEntity<Iterable<ProductSku>> getAllProductSkus() {
        return ResponseEntity.ok(productSkuService.findAll());
    }

    @PostMapping("/product-sku")
    public ResponseEntity<?> createProductSku(
            @Valid @RequestBody ProductSkuCreationDto productSku, 
            BindingResult result
        ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }
        Optional<ProductSku> productSkuOptional = productSkuService.save(productSku);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors()
                .forEach(err -> errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/product-sku/{id}")
    public ResponseEntity<?> updateProductSku(
        @Valid @RequestBody ProductSku productSku, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return this.validation(result);
        }

        Optional<ProductSku> productSkuOptional = productSkuService.update(productSku, id);

        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/product-sku/{id}")
    public ResponseEntity<?> deleteProductSku(@PathVariable Long id) {
        Optional<ProductSku> productSkuOptional = productSkuService.findById(id);

        if (productSkuOptional.isPresent()) {
            productSkuService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
