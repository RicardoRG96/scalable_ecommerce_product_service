package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;

public interface ProductGalleryService {

    Optional<ProductGallery> findById(Long id);

    Optional<List<ProductGallery>> findByProductId(Long id);

    Optional<List<ProductGallery>> findByColorAttributeId(Long id);

    Optional<ProductGallery> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId);

    Iterable<ProductGallery> findAll();

    Optional<ProductGallery> save(ProductGalleryCreationDto productGallery);

    Optional<ProductGallery> update(ProductGallery productGallery, Long id);

    void delete(Long id);

}
