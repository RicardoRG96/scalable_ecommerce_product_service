package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.BrandRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

@Service
public class BrandServiceImpl implements BrandService{

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }

    @Override
    public Iterable<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Brand save(BrandCreationDto brandCreation) {
        Brand brand = new Brand();
        brand.setName(brandCreation.getName());
        brand.setDescription(brandCreation.getDescription());
        brand.setLogoUrl(brandCreation.getLogoUrl());

        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> update(Brand brand, Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        
        return brandOptional.map(dbBrand -> {
            dbBrand.setName(brand.getName());
            dbBrand.setDescription(brand.getDescription());
            dbBrand.setLogoUrl(brand.getLogoUrl());

            return Optional.of(brandRepository.save(dbBrand));
        }).orElseGet(Optional::empty);
    }

    @Override
    public void delete(Long id) {
        Brand unknownBrand = brandRepository.findByName("Unknown Brand").orElseThrow();
        List<Product> products = productRepository.findByBrandId(id).orElseThrow();

        products.forEach(product -> {
            product.setBrand(unknownBrand);
            productRepository.save(product);
        });
        
        brandRepository.deleteById(id);
    }

}
