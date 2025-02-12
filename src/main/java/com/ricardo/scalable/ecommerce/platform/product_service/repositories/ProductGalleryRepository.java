package com.ricardo.scalable.ecommerce.platform.product_service.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;

public interface ProductGalleryRepository extends CrudRepository<ProductGallery, Long> {

    Optional<List<ProductGallery>> findByProductId(Long id);

    Optional<List<ProductGallery>> findByColorAttributeId(Long id);

    Optional<ProductGallery> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId);

    Optional<ProductGallery> findByImageUrl(String imageUrl);

}
