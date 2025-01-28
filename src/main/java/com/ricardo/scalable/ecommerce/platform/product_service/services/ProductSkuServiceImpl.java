package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

@Service
public class ProductSkuServiceImpl implements ProductSkuService {
    
    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Override
    public Optional<ProductSku> findByProductId(Long id) {
        return productSkuRepository.findByProductId(id);
    }

    @Override
    public Optional<ProductSku> findBySku(String sku) {
        return productSkuRepository.findBySku(sku);
    }

    @Override
    public Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive) {
        return productSkuRepository.findBySkuAndIsActive(sku, isActive);
    }

    @Override
    public Optional<ProductSku> save(ProductSkuCreationDto productSku) {
        Optional<Product> product = productRepository.findById(productSku.getProductId());
        Optional<ProductAttribute> sizeAttribute = productAttributeRepository.findById(productSku.getSizeAttributeId());
        Optional<ProductAttribute> colorAttribute = productAttributeRepository.findById(productSku.getColorAttributeId());

        if (product.isPresent() && sizeAttribute.isPresent() && colorAttribute.isPresent()) {
            ProductSku newProductSku = new ProductSku();

            newProductSku.setProduct(product.get());
            newProductSku.setSizeAttribute(sizeAttribute.get());
            newProductSku.setColorAttribute(colorAttribute.get());
            newProductSku.setSku(productSku.getSku());
            newProductSku.setPrice(productSku.getPrice());
            newProductSku.setStock(productSku.getStock());
            newProductSku.setIsActive(productSku.getIsActive());
            newProductSku.setIsFeatured(productSku.getIsFeatured());
            newProductSku.setIsOnSale(productSku.getIsOnSale());
            return Optional.of(productSkuRepository.save(newProductSku));
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductSku> update(ProductSku productSku, Long id) {
        Optional<ProductSku> existingProductSku = productSkuRepository.findById(id);

        return existingProductSku.map(dbProductSku -> {
            dbProductSku.setProduct(productSku.getProduct());
            dbProductSku.setSizeAttribute(productSku.getSizeAttribute());
            dbProductSku.setColorAttribute(productSku.getColorAttribute());
            dbProductSku.setSku(productSku.getSku());
            dbProductSku.setPrice(productSku.getPrice());
            dbProductSku.setStock(productSku.getStock());
            dbProductSku.setIsActive(productSku.getIsActive());
            dbProductSku.setIsFeatured(productSku.getIsFeatured());
            dbProductSku.setIsOnSale(productSku.getIsOnSale());
            return Optional.of(productSkuRepository.save(dbProductSku));
        }).orElseGet(Optional::empty);
    }

    @Override
    public void delete(Long id) {
        productSkuRepository.deleteById(id);
    }

}
