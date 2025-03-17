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
import org.springframework.web.bind.annotation.RestController;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.DiscountCode;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.DiscountCodeDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.DiscountCodeService;

import static com.ricardo.scalable.ecommerce.platform.product_service.controllers.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class DiscountCodeController {

    @Autowired
    private DiscountCodeService discountCodeService;

    @GetMapping("/discount-code/{id}")
    public ResponseEntity<DiscountCode> getDiscountCodeById(@PathVariable Long id) {
        Optional<DiscountCode> discountCode = discountCodeService.findById(id);
        if (discountCode.isPresent()) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code/code/{code}")
    public ResponseEntity<DiscountCode> getDiscountCodeByCode(@PathVariable String code) {
        Optional<DiscountCode> discountCode = discountCodeService.findByCode(code);
        if (discountCode.isPresent()) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code/discount-id/{discountId}")
    public ResponseEntity<List<DiscountCode>> getDiscountCodeByDiscountId(@PathVariable Long discountId) {
        Optional<List<DiscountCode>> discountCode = discountCodeService.findByDiscountId(discountId);
        boolean isPresent = discountCode.isPresent();
        boolean isEmpty = discountCode.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code/code/{code}/discount-id/{discountId}")
    public ResponseEntity<DiscountCode> getDiscountCodeByCodeAndDiscountId(@PathVariable String code, @PathVariable Long discountId) {
        Optional<DiscountCode> discountCode = discountCodeService.findByCodeAndDiscountId(code, discountId);
        if (discountCode.isPresent()) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code/usage-limit/{usageLimit}")
    public ResponseEntity<List<DiscountCode>> getDiscountCodeByUsageLimit(@PathVariable Integer usageLimit) {
        Optional<List<DiscountCode>> discountCode = discountCodeService.findByUsageLimit(usageLimit);
        boolean isPresent = discountCode.isPresent();
        boolean isEmpty = discountCode.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code/used-count/{usedCount}")
    public ResponseEntity<List<DiscountCode>> getDiscountCodeByUsedCount(@PathVariable Integer usedCount) {
        Optional<List<DiscountCode>> discountCode = discountCodeService.findByUsedCount(usedCount);
        boolean isPresent = discountCode.isPresent();
        boolean isEmpty = discountCode.orElseThrow().isEmpty();

        if (isPresent && !isEmpty) {
            return ResponseEntity.ok(discountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/discount-code")
    public ResponseEntity<List<DiscountCode>> getAllDiscountCodes() {
        List<DiscountCode> discountCodes = discountCodeService.findAll();
        if (!discountCodes.isEmpty()) {
            return ResponseEntity.ok(discountCodes);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/discount-code")
    public ResponseEntity<?> createDiscountCode(
        @Valid @RequestBody DiscountCodeDto discountCode, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<DiscountCode> newDiscountCode = discountCodeService.save(discountCode);
        if (newDiscountCode.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newDiscountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/discount-code/{id}")
    public ResponseEntity<?> updateDiscountCode(
        @Valid @RequestBody DiscountCodeDto discountCode, 
        @PathVariable Long id, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<DiscountCode> updatedDiscountCode = discountCodeService.update(discountCode, id);
        if (updatedDiscountCode.isPresent()) {
            return ResponseEntity.ok(updatedDiscountCode.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/discount-code/{id}")
    public ResponseEntity<?> deleteDiscountCode(@PathVariable Long id) {
        Optional<DiscountCode> discountCode = discountCodeService.findById(id);
        if (discountCode.isPresent()) {
            discountCodeService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
