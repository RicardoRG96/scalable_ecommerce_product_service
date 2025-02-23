package com.ricardo.scalable.ecommerce.platform.product_service.controllers.validation;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class ProductGalleryFormInputValidation {

    public static ResponseEntity<?> validateFormData(
        String productName, 
        String colorAttributeName, 
        List<MultipartFile> images
    ) {
        if (!isValidProductName(productName)) {
            return ResponseEntity.badRequest().body("El nombre del producto es requerido");
        }
        if (!isValidColorAttributeName(colorAttributeName)) {
            return ResponseEntity.badRequest().body("El nombre del atributo de color es requerido");
        }
        ResponseEntity<?> validateImages = isValidImages(images);
        if (validateImages != null) {
            return validateImages;
        }
        return null;
    }

    private static boolean isValidProductName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private static boolean isValidColorAttributeName(String colorAttributeName) {
        if (colorAttributeName == null || colorAttributeName.trim().isEmpty()) {
            return false;
        }
        return true;
    }

    private static ResponseEntity<?> isValidImages(List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return ResponseEntity.badRequest().body("Las imágenes son requeridas");
        }

        ResponseEntity<?> validateImagesNotEmpty = validateImagesNotEmpty(images);
        if (validateImagesNotEmpty != null) {
            return validateImagesNotEmpty;
        }

        ResponseEntity<?> validateImagesName = validateImagesName(images);
        if (validateImagesName != null) {
            return validateImagesName;
        }

        ResponseEntity<?> validateImagesFormat = validateImagesFormat(images);
        if (validateImagesFormat != null) {
            return validateImagesFormat;
        }

        ResponseEntity<?> validateImagesSize = validateImagesSize(images);
        if (validateImagesSize != null) {
            return validateImagesSize;
        }

        return null;
    }

    private static ResponseEntity<?> validateImagesNotEmpty(List<MultipartFile> images) {
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                return ResponseEntity.badRequest().body("Las imágenes no pueden estar vacías");
            }
        }
        return null;
    }

    @SuppressWarnings("null")
    private static ResponseEntity<?> validateImagesName(List<MultipartFile> images) {
        for (MultipartFile image : images) {
            String originalFilename = image.getOriginalFilename();

            if (originalFilename.contains("..") || 
                originalFilename.contains("/") ||
                originalFilename.contains("\\")
            ) {
                return ResponseEntity.badRequest().body("Nombre de archivo no válido");
            }
        }
        return null;
    }

    @SuppressWarnings("null")
    private static ResponseEntity<?> validateImagesFormat(List<MultipartFile> images) {
        for (MultipartFile image : images) {
            String contentType = image.getContentType();

            if (!contentType.equals("image/jpeg") && 
                !contentType.equals("image/png") &&
                !contentType.equals("image/webp")
            ) {
                return ResponseEntity.badRequest().body("Las imágenes deben ser de tipo JPEG, PNG o WebP");
            }
        }
        return null;
    }

    private static ResponseEntity<?> validateImagesSize(List<MultipartFile> images) {
        int megabyteConversion = 1024 * 1024;
        int maxNumberOfMegabyteAllowed = 2;
        int maxSize = maxNumberOfMegabyteAllowed * megabyteConversion;

        for (MultipartFile image : images) {
            if (image.getSize() > maxSize) {
                return ResponseEntity.badRequest().body("Las imágenes no pueden pesar más de 2MB");
            }
        }
        return null;
    }

}
