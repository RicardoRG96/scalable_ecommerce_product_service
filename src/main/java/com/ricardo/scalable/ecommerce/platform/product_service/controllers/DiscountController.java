package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

import java.time.LocalDateTime;
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

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Discount;
import com.ricardo.scalable.ecommerce.platform.libs_common.enums.DiscountType;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.DiscountService;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class DiscountController {

    @Autowired
    private DiscountService discountService;

    @GetMapping("/discounts/{id}")
    public ResponseEntity<Discount> getDiscountById(@PathVariable Long id) {
        Optional<Discount> discount = discountService.findById(id);
        if (discount.isPresent()) {
            return ResponseEntity.ok(discount.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discounts/product_sku/{productSkuId}")
    public ResponseEntity<List<Discount>> getDiscountByProductSkuId(@PathVariable Long productSkuId) {
        Optional<List<Discount>> discount = discountService.findByProductSkuId(productSkuId);
        boolean isPresent = discount.isPresent();
        boolean isEmpty = discount.orElseThrow().isEmpty();
        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(discount.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discounts/discount_type/{discountType}")
    public ResponseEntity<List<Discount>> getDiscountByType(@PathVariable String discountType) {
        try {
            DiscountType discountTypeEnum = DiscountType.valueOf(discountType.toUpperCase());
            Optional<List<Discount>> discount = discountService.findByDiscountType(discountTypeEnum);
            boolean isPresent = discount.isPresent();
            boolean isEmpty = discount.orElseThrow().isEmpty();
            if (isPresent && !isEmpty) {
                return ResponseEntity.ok(discount.orElseThrow());
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
        
    }

    @GetMapping("/discounts/discount_value/{discountValue}")
    public ResponseEntity<List<Discount>> getDiscountByValue(@PathVariable Double discountValue) {
        Optional<List<Discount>> discount = discountService.findByDiscountValue(discountValue);
        boolean isPresent = discount.isPresent();
        boolean isEmpty = discount.orElseThrow().isEmpty();
        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(discount.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discounts/validity/{discountId}")
    public ResponseEntity<Discount> verifyValidityPeriod(@PathVariable Long discountId) {
        Optional<Discount> discount = discountService.verifyValidityPeriod(discountId);
        if (discount.isPresent()) {
            return ResponseEntity.ok(discount.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discounts/overlapping/{productSkuId}/{newStartDate}/{newEndDate}")
    public ResponseEntity<Map<String, Integer>> checkOverlappingDiscount(
        @PathVariable Long productSkuId,
        @PathVariable LocalDateTime newStartDate, 
        @PathVariable LocalDateTime newEndDate
    ) {
        Map<String, Integer> mapResponse = new HashMap<>();
        int count = discountService.checkOverlappingDiscount(productSkuId, newStartDate, newEndDate);
        mapResponse.put("overlappingDiscounts", count);
        return ResponseEntity.ok(mapResponse);
    }

    @GetMapping("/discounts")
    public ResponseEntity<?> getAllDiscounts() {
        return ResponseEntity.ok(discountService.findAll());
    }

    @PostMapping("/discounts")
    public ResponseEntity<?> createDiscount(
        @Valid @RequestBody DiscountDto discount, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        try {
            Optional<Discount> newDiscount = discountService.save(discount);
            if (newDiscount.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(newDiscount.orElseThrow());
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid discount type provided.");
        }
    }

    @PutMapping("/discounts/{id}")
    public ResponseEntity<?> updateDiscount( 
        @Valid @RequestBody DiscountDto discount,
        @PathVariable Long id, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Discount> updatedDiscount = discountService.update(discount, id);
        if (updatedDiscount.isPresent()) {
            return ResponseEntity.ok(updatedDiscount.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/discounts/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        Optional<Discount> discount = discountService.findById(id);
        if (discount.isPresent()) {
            discountService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
