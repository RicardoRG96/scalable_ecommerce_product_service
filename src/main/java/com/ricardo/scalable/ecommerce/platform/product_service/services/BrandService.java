package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;

public interface BrandService {

    Optional<Brand> findById(Long id);

    Optional<Brand> findByName(String name);

    Iterable<Brand> findAll();

    Brand save(Brand brand);

    Optional<Brand> update(Brand brand, Long id);

    void delete(Long id);

}
