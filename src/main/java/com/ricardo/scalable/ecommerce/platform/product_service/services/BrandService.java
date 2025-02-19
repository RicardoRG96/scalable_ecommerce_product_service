package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.BrandCreationDto;

public interface BrandService {

    Optional<Brand> findById(Long id);

    Optional<Brand> findByName(String name);

    Iterable<Brand> findAll();

    Brand save(BrandCreationDto brandCreation);

    Optional<Brand> update(Brand brand, Long id);

    void delete(Long id);

}
