package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
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
    @Transactional(readOnly = true)
    public Optional<ProductSku> findById(Long id) {
        return productSkuRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductSku>> findByProductId(Long id) {
        return productSkuRepository.findByProductId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSku> findBySku(String sku) {
        return productSkuRepository.findBySku(sku);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSku> findBySkuAndIsActive(String sku, Boolean isActive) {
        return productSkuRepository.findBySkuAndIsActive(sku, isActive);
    }

    @Override
    public Optional<List<ProductSku>> findBySizeAttributeId(Long sizeAttributeId) {
        return productSkuRepository.findBySizeAttributeId(sizeAttributeId);
    }

    @Override
    public Optional<List<ProductSku>> findByColorAttributeId(Long colorAttributeId) {
        return productSkuRepository.findByColorAttributeId(colorAttributeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductSku>> findByProductIdAndSizeAttributeId(
            Long productId, 
            Long sizeAttributeId
        ) {
        return productSkuRepository.findByProductIdAndSizeAttributeId(productId, sizeAttributeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductSku>> findByProductIdAndColorAttributeId(
            Long productId, 
            Long colorAttributeId
        ) {
        return productSkuRepository.findByProductIdAndColorAttributeId(productId, colorAttributeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductSku> findByProductIdAndSizeAttributeIdAndColorAttributeId(
            Long productId, 
            Long sizeAttributeId, 
            Long colorAttributeId
        ) {
        return productSkuRepository.findByProductIdAndSizeAttributeIdAndColorAttributeId(
            productId, sizeAttributeId, colorAttributeId
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ProductSku> findAll() {
        return productSkuRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<ProductSku> save(ProductSkuCreationDto productSku) {
        Optional<Product> productOptional = productRepository.findById(productSku.getProductId());
        Optional<ProductAttribute> sizeAttributeOptional = productAttributeRepository.findById(productSku.getSizeAttributeId());
        Optional<ProductAttribute> colorAttributeOptional = productAttributeRepository.findById(productSku.getColorAttributeId());

        if (
            productOptional.isPresent() && 
            sizeAttributeOptional.isPresent() && 
            colorAttributeOptional.isPresent()
        ) {
            ProductSku newProductSku = new ProductSku();

            newProductSku.setProduct(productOptional.orElseThrow());
            newProductSku.setSizeAttribute(sizeAttributeOptional.orElseThrow());
            newProductSku.setColorAttribute(colorAttributeOptional.orElseThrow());
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
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        productSkuRepository.deleteById(id);
    }

}
