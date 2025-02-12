package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductGalleryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class ProductGalleryServiceImpl implements ProductGalleryService {

    @Autowired
    private ProductGalleryRepository productGalleryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductGallery> findById(Long id) {
        return productGalleryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductGallery>> findByProductId(Long id) {
        return productGalleryRepository.findByProductId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductGallery>> findByColorAttributeId(Long id) {
        return productGalleryRepository.findByColorAttributeId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductGallery> findByProductIdAndColorAttributeId(Long productId, Long colorAttributeId) {
        return productGalleryRepository.findByProductIdAndColorAttributeId(productId, colorAttributeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductGallery> findByImageUrl(String imageUrl) {
        return productGalleryRepository.findByImageUrl(imageUrl);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ProductGallery> findAll() {
        return productGalleryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<ProductGallery> save(ProductGalleryCreationDto productGallery) {
        Long productId = productGallery.getProductId();
        Long colorAttributeId = productGallery.getColorAttributeId();
        Optional<Product> product = productRepository.findById(productId);
        Optional<ProductAttribute> colorAttribute = productAttributeRepository.findById(colorAttributeId);

        if (product.isPresent() && colorAttribute.isPresent()) {
            ProductGallery newProductGallery = new ProductGallery();
            newProductGallery.setProduct(product.orElseThrow());
            newProductGallery.setColorAttribute(colorAttribute.orElseThrow());
            newProductGallery.setImageUrl(productGallery.getImageUrl());
            return Optional.of(productGalleryRepository.save(newProductGallery));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<ProductGallery> update(ProductGallery productGallery, Long id) {
        Optional<ProductGallery> existingProductGallery = productGalleryRepository.findById(id);

        return existingProductGallery.map(dbProductGallery -> {
            dbProductGallery.setProduct(productGallery.getProduct());
            dbProductGallery.setColorAttribute(productGallery.getColorAttribute());
            dbProductGallery.setImageUrl(productGallery.getImageUrl());
            return Optional.of(productGalleryRepository.save(dbProductGallery));
        }).orElseGet(Optional::empty);
        
    }

    @Override
    @Transactional
    public void delete(Long id) {
        productGalleryRepository.deleteById(id);
    }

}
