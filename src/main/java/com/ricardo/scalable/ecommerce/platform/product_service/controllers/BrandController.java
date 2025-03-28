package com.ricardo.scalable.ecommerce.platform.product_service.controllers;

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

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.services.BrandService;

import static com.ricardo.scalable.ecommerce.platform.libs_common.validation.RequestBodyValidation.*;

import jakarta.validation.Valid;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/brands/{id}")
    public ResponseEntity<Brand> getById(@PathVariable Long id) {
        Optional<Brand> brandOptional = brandService.findById(id);

        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/brands/name/{name}")
    public ResponseEntity<Brand> getByName(@PathVariable String name) {
        Optional<Brand> brandOptional = brandService.findByName(name);

        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/brands")
    public ResponseEntity<Iterable<Brand>> getAllBrands() {
        return ResponseEntity.ok(brandService.findAll());
    }

    @PostMapping("/brands")
    public ResponseEntity<?> createBrand(
        @Valid @RequestBody BrandCreationDto brandCreation, 
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.save(brandCreation));
    }

    @PutMapping("/brands/{id}")
    public ResponseEntity<?> updateBrand(
        @Valid @RequestBody Brand brand, 
        @PathVariable Long id,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Brand> brandOptional = brandService.update(brand, id);

        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/brands/{id}")
    public ResponseEntity<Brand> deleteBrand(@PathVariable Long id) {
        Optional<Brand> brandOptional = brandService.findById(id);

        if (brandOptional.isPresent()) {
            brandService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
