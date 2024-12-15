package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;

public interface BrandRepository extends CrudRepository<Brand, Long> {

}
