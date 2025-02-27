package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductAttributeRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductGalleryRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;
import com.ricardo.scalable.ecommerce.platform.product_service.storageService.StorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProductGalleryServiceImpl implements ProductGalleryService {

    @Autowired
    private ProductGalleryRepository productGalleryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductAttributeRepository productAttributeRepository;

    @Autowired
    private StorageService storageService;

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
    public Iterable<ProductGallery> findAll() {
        return productGalleryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<ProductGallery> save(ProductGalleryCreationDto productGallery) {
        String productName = productGallery.getProductName();
        String colorName = productGallery.getColorName();
        Optional<Product> product = productRepository.findByName(productName);
        Optional<ProductAttribute> colorAttribute = productAttributeRepository.findByValue(colorName);
        List<MultipartFile> images = productGallery.getImages();

        if (product.isPresent() && colorAttribute.isPresent()) {
            ProductGallery newProductGallery = new ProductGallery();
            newProductGallery.setProduct(product.orElseThrow());
            newProductGallery.setColorAttribute(colorAttribute.orElseThrow());
            storeImages(images);
            // newProductGallery.setImageUrl(productGallery.getImageUrl());
            return Optional.of(productGalleryRepository.save(newProductGallery));
        }
        return Optional.empty();
    }

    private void storeImages(List<MultipartFile> images) {
        List<File> fileImages = new ArrayList<>();
        // Convert MultipartFile to File.
        images.forEach(image -> {
            try {
                fileImages.add(multipartFileToFile(image));
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        });
        
        fileImages.forEach(file -> {
            storageService.store(file);
        });
    }

    private File multipartFileToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        String fileName = renameFile(multipartFile.getOriginalFilename());
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        
        multipartFile.transferTo(convFile);
        return convFile;
    }

    private String renameFile(String fileName) {
        Timestamp now = Timestamp.from(Instant.now());
        fileName = now + "_" + fileName;
        return fileName;
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
