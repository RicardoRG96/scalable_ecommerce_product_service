package com.ricardo.scalable.ecommerce.platform.product_service.services;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
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
        Optional<Product> product = productRepository.findByName(productGallery.getProductName());
        Optional<ProductAttribute> colorAttribute = productAttributeRepository.findByValue(productGallery.getColorName());

        if (
            product.isPresent() && 
            colorAttribute.isPresent()
        ) {
            Optional<String> imageUrl = storeImage(productGallery.getImage());
            ProductGallery newProductGallery = new ProductGallery();
            newProductGallery.setProduct(product.orElseThrow());
            newProductGallery.setColorAttribute(colorAttribute.orElseThrow());
            newProductGallery.setImageUrl(imageUrl.orElseThrow());
            return Optional.of(productGalleryRepository.save(newProductGallery));
        }
        return Optional.empty();
    }

    private Optional<String> storeImage(MultipartFile image) {
        try {
            File imageFile = multipartFileToFile(image);
            Optional<String> storedImage = storageService.store(imageFile);
            
            if (storedImage.isPresent()) {
                Optional<String> imageUrl = storageService.getImageUrl(imageFile.getName());
                return imageUrl;
            }
            return Optional.empty();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private File multipartFileToFile(MultipartFile multipartFile) throws IllegalStateException, IOException {
        String fileName = renameFile(multipartFile.getOriginalFilename());
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + fileName);
        
        multipartFile.transferTo(convFile);
        return convFile;
    }

    private String renameFile(String fileName) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Timestamp now = Timestamp.from(Instant.now());
        String formatedDate = dateFormater.format(now);
        fileName = formatedDate + "_" + fileName;
        return fileName;
    }

    @Override
    @Transactional
    public Optional<ProductGallery> update(ProductGalleryCreationDto productGallery, Long id) {
        Optional<ProductGallery> existingProductGallery = productGalleryRepository.findById(id);
        Optional<Product> product = productRepository.findByName(productGallery.getProductName());
        Optional<ProductAttribute> colorAttribute = productAttributeRepository.findByValue(productGallery.getColorName());

        if (existingProductGallery.isPresent() &&
            product.isPresent() && 
            colorAttribute.isPresent()
        ) {
            Optional<String> imageUrl = storeImage(productGallery.getImage());
            ProductGallery dbProductGallery = existingProductGallery.orElseThrow();
            dbProductGallery.setProduct(product.orElseThrow());
            dbProductGallery.setColorAttribute(colorAttribute.orElseThrow());
            dbProductGallery.setImageUrl(imageUrl.orElseThrow());
            return Optional.of(productGalleryRepository.save(dbProductGallery));
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ProductGallery productGallery = productGalleryRepository.findById(id).orElseThrow();
        String imageUrl = productGallery.getImageUrl();
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
        storageService.delete(fileName);
        productGalleryRepository.deleteById(id);
    }

}
