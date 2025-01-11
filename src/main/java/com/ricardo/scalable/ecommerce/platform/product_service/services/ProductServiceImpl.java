package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.BrandRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.CategoryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductCreationDto;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

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
    public Optional<Product> save(ProductCreationDto productCreation) {
        Optional<Category> category = categoryRepository.findById(productCreation.getCategoryId());
        Optional<Brand> brand = brandRepository.findById(productCreation.getBrandId());

        if (category.isPresent() && brand.isPresent()) {
            Product product = new Product();

            product.setSku(productCreation.getSku());
            product.setUpc(productCreation.getUpc());
            product.setName(productCreation.getName());
            product.setDescription(productCreation.getDescription());
            product.setCategory(category.get());
            product.setBrand(brand.get());
            product.setPrice(productCreation.getPrice());
            product.setStock(productCreation.getStock());
            product.setImageUrl(productCreation.getImageUrl());
            product.setIsActive(productCreation.getIsActive());
            product.setIsFeatured(productCreation.getIsFeatured());
            product.setIsOnSale(productCreation.getIsOnSale());

            return Optional.of(productRepository.save(product));
        }

        return Optional.empty();
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
