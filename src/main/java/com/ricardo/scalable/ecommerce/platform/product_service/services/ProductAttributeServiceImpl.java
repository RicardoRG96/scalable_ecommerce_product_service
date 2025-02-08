package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductAttributeCreationDto;

@Service
public class ProductAttributeServiceImpl implements ProductAttributeService {

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private ProductSkuRepository productSkuRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttribute> findById(Long id) {
        return productAttributeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<ProductAttribute>> findByType(String type) {
        return productAttributeRepository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProductAttribute> findByValue(String value) {
        return productAttributeRepository.findByValue(value);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<ProductAttribute> findAll() {
        return productAttributeRepository.findAll();
    }

    @Override
    @Transactional
    public ProductAttribute save(ProductAttributeCreationDto productAttribute) {
        ProductAttribute attribute = new ProductAttribute();
        attribute.setType(productAttribute.getType());
        attribute.setValue(productAttribute.getValue());

        return productAttributeRepository.save(attribute);
    }

    @Override
    @Transactional
    public Optional<ProductAttribute> update(ProductAttribute productAttribute, Long id) {
        Optional<ProductAttribute> attributeOptional = productAttributeRepository.findById(id);
        if (attributeOptional.isPresent()) {
            ProductAttribute dbAttribute = attributeOptional.orElseThrow();
            dbAttribute.setType(productAttribute.getType());
            dbAttribute.setValue(productAttribute.getValue());

            return Optional.of(productAttributeRepository.save(dbAttribute));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductAttribute productAttributeToDelete = productAttributeRepository.findById(id).orElseThrow();
        boolean isColorType = productAttributeToDelete.getType().equals("color");
        boolean isSizeType = productAttributeToDelete.getType().equals("size");

        if (isColorType) {
            ProductAttribute noneColor = productAttributeRepository.findByValue("none-color").orElseThrow();
            List<ProductSku> productSkusWithColorToDelete = 
                (List<ProductSku>) productSkuRepository.findByColorAttributeId(id).orElseThrow();
            productSkusWithColorToDelete.forEach(productSku -> {
                productSku.setColorAttribute(noneColor);
                productSkuRepository.save(productSku);
            });
        } 

        if (isSizeType) {
            ProductAttribute noneSize = productAttributeRepository.findByValue("none-size").orElseThrow();
            List<ProductSku> productSkusWithSizeToDelete = 
                (List<ProductSku>) productSkuRepository.findBySizeAttributeId(id).orElseThrow();
            productSkusWithSizeToDelete.forEach(productSku -> {
                productSku.setSizeAttribute(noneSize);
                productSkuRepository.save(productSku);
            });   
        }

        productAttributeRepository.deleteById(id);
    }

}
