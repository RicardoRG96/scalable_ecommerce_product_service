package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long> {

    Optional<Brand> findByName(String name);

}
