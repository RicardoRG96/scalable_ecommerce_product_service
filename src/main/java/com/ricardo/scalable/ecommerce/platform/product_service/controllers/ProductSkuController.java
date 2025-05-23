package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.searchService.SearchServiceClient;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductSkuService;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class ProductSkuController {

    @Autowired
    private ProductSkuService productSkuService;
    
    @Autowired
    private SearchServiceClient searchServiceClient;

    @GetMapping("/product-sku/{id}")
    public ResponseEntity<ProductSku> getById(@PathVariable Long id) {
        Optional<ProductSku> productSkuOptional = productSkuService.findById(id);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.ok(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/product/{productId}")
    public ResponseEntity<List<ProductSku>> getByProductId(@PathVariable Long productId) {
        Optional<List<ProductSku>> productSkus =  productSkuService.findByProductId(productId);
        boolean isPresent = productSkus.isPresent();
        boolean isEmpty = productSkus.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productSkus.orElseThrow());
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
    public ResponseEntity<List<ProductSku>> getBySizeAttributeId(@PathVariable Long sizeAttributeId) {
        Optional<List<ProductSku>> productSkus = productSkuService.findBySizeAttributeId(sizeAttributeId);
        boolean isPresent = productSkus.isPresent();
        boolean isEmpty = productSkus.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productSkus.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/colorAttributeId/{colorAttributeId}")
    public ResponseEntity<List<ProductSku>> getByColorAttributeId(@PathVariable Long colorAttributeId) {
        Optional<List<ProductSku>> productSkus = productSkuService.findByColorAttributeId(colorAttributeId);
        boolean isPresent = productSkus.isPresent();
        boolean isEmpty = productSkus.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productSkus.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/productId/{productId}/sizeAttributeId/{sizeAttributeId}")
    public ResponseEntity<List<ProductSku>> getByProductIdAndSizeAttributeId(
            @PathVariable Long productId, 
            @PathVariable Long sizeAttributeId
        ) {
        Optional<List<ProductSku>> productSkus = 
            productSkuService.findByProductIdAndSizeAttributeId(productId, sizeAttributeId);

        boolean isPresent = productSkus.isPresent();
        boolean isEmpty = productSkus.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productSkus.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product-sku/productId/{productId}/colorAttributeId/{colorAttributeId}")
    public ResponseEntity<List<ProductSku>> getByProductIdAndColorAttributeId(
            @PathVariable Long productId, 
            @PathVariable Long colorAttributeId
        ) {
        Optional<List<ProductSku>> productSkus = 
            productSkuService.findByProductIdAndColorAttributeId(productId, colorAttributeId);

        boolean isPresent = productSkus.isPresent();
        boolean isEmpty = productSkus.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(productSkus.orElseThrow());
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

    /*
     * BEGIN OF SEARCH SERVICE ENDPOINTS
     */
    
    @GetMapping("/search")
    public ResponseEntity<List<ProductSku>> searchProducts(@RequestParam String query) {
        List<ProductSku> searchResults = searchServiceClient.searchProducts(query);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/brand")
    public ResponseEntity<List<ProductSku>> filterByBrand(@RequestParam List<String> brand) {
        List<ProductSku> searchResults = searchServiceClient.filterByBrand(brand);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/category")
    public ResponseEntity<List<ProductSku>> filterByCategory(@RequestParam List<String> category) {
        List<ProductSku> searchResults = searchServiceClient.filterByCategory(category);
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/price")
    public ResponseEntity<List<ProductSku>> filterByPriceRange(
        @RequestParam Double minPrice, 
        @RequestParam Double maxPrice
    ) {
        List<ProductSku> searchResults = searchServiceClient.filterByPriceRange(minPrice, maxPrice);
        return ResponseEntity.ok(searchResults);
    } 

    /*
     * END OF SEARCH SERVICE ENDPOINTS
     */

    @PostMapping("/product-sku")
    public ResponseEntity<?> createProductSku(
        @Valid @RequestBody ProductSkuCreationDto productSku, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<ProductSku> productSkuOptional = productSkuService.save(productSku);
        if (productSkuOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(productSkuOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/product-sku/{id}")
    public ResponseEntity<?> updateProductSku(
        @Valid @RequestBody ProductSku productSku, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
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
            searchServiceClient.deleteRecord(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
