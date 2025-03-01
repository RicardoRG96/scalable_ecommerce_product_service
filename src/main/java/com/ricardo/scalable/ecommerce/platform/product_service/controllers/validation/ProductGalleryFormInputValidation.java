package com.ricardo.scalable.ecommerce.platform.product_service.controllers.validation;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class ProductGalleryFormInputValidation {

    public static ResponseEntity<?> validateFormData(
        String productName, 
        String colorAttributeName, 
        MultipartFile image
    ) {
        if (!isValidProductName(productName)) {
            return ResponseEntity.badRequest().body("El nombre del producto es requerido");
        }
        if (!isValidColorAttributeName(colorAttributeName)) {
            return ResponseEntity.badRequest().body("El nombre del atributo de color es requerido");
        }
        ResponseEntity<?> validateImage = isValidImage(image);
        if (validateImage != null) {
            return validateImage;
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

    private static ResponseEntity<?> isValidImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return ResponseEntity.badRequest().body("Las imágenes son requeridas");
        }

        ResponseEntity<?> validateImageNotEmpty = validateImageNotEmpty(image);
        if (validateImageNotEmpty != null) {
            return validateImageNotEmpty;
        }

        ResponseEntity<?> validateImageName = validateImageName(image);
        if (validateImageName != null) {
            return validateImageName;
        }

        ResponseEntity<?> validateImageFormat = validateImageFormat(image);
        if (validateImageFormat != null) {
            return validateImageFormat;
        }

        ResponseEntity<?> validateImageSize = validateImageSize(image);
        if (validateImageSize != null) {
            return validateImageSize;
        }

        return null;
    }

    private static ResponseEntity<?> validateImageNotEmpty(MultipartFile image) {
        if (image.isEmpty()) {
            return ResponseEntity.badRequest().body("Las imágenes no pueden estar vacías");
        }
        return null;
    }

    @SuppressWarnings("null")
    private static ResponseEntity<?> validateImageName(MultipartFile image) {
        String originalFilename = image.getOriginalFilename();

        if (originalFilename.contains("..") || 
            originalFilename.contains("/") ||
            originalFilename.contains("\\")
        ) {
            return ResponseEntity.badRequest().body("Nombre de archivo no válido");
        }
        return null;
    }

    @SuppressWarnings("null")
    private static ResponseEntity<?> validateImageFormat(MultipartFile image) {
        String contentType = image.getContentType();

        if (!contentType.equals("image/jpeg") &&
            !contentType.equals("image/png") &&
            !contentType.equals("image/webp")
        ) {
            return ResponseEntity.badRequest().body("Las imágenes deben ser de tipo JPEG, PNG o WebP");
        }
        return null;
    }

    private static ResponseEntity<?> validateImageSize(MultipartFile image) {
        int megabyteConversion = 1024 * 1024;
        int maxNumberOfMegabyteAllowed = 2;
        int maxSize = maxNumberOfMegabyteAllowed * megabyteConversion;

        if (image.getSize() > maxSize) {
            Map<String, String> errors = new HashMap<>();
            errors.put("message", "Las imágenes no pueden pesar más de 2MB");
            return ResponseEntity.badRequest().body(errors);
        }
        
        return null;
    }

}
