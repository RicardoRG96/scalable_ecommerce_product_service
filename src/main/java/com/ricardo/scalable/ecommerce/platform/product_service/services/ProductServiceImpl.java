package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> findBySku(String sku) {
        return productRepository.findBySku(sku);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    @Transactional
    public Optional<Product> update(Product product, Long id) {
        Optional<Product> productOptional = productRepository.findById(id);

        return productOptional.map(dbProduct -> {
            dbProduct.setName(product.getName());
            dbProduct.setDescription(product.getDescription());
            dbProduct.setCategory(product.getCategory());
            dbProduct.setBrand(product.getBrand());
            dbProduct.setPrice(product.getPrice());
            dbProduct.setStock(product.getStock());
            dbProduct.setImageUrl(product.getImageUrl());
            dbProduct.setIsActive(product.getIsActive());
            dbProduct.setIsFeatured(product.getIsFeatured());
            dbProduct.setIsOnSale(product.getIsOnSale());
            
            return Optional.of(productRepository.save(dbProduct));
        }).orElseGet(Optional::empty);
    }

    @Override
    public void delete(Long id) {
        productRepository.deleteById(id);
    }

}
