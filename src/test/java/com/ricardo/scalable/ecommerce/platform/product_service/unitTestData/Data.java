package com.ricardo.scalable.ecommerce.platform.product_service.unitTestData;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;

public class Data {

    public static List<Product> createListOfProducts() {
        Product product1 = createProduct001().orElseThrow();
        Product product2 = createProduct002().orElseThrow();
        Product product3 = createProduct003().orElseThrow();
        Product product4 = createProduct004().orElseThrow();
        Product product5 = createProduct005().orElseThrow();

        return List.of(product1, product2, product3, product4, product5);
    }

    public static Optional<Product> createProduct001() {
        Product product = new Product();
        Category category = createSubCategory001().orElseThrow();
        Brand brand = createBrand002().orElseThrow();

        product.setId(1L);
        product.setName("Notebook Samsung");
        product.setDescription("Computador de ultima generacion Samsung");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct002() {
        Product product = new Product();
        Category category = createSubCategory002().orElseThrow();
        Brand brand = createBrand001().orElseThrow();

        product.setId(2L);
        product.setName("iPhone Apple");
        product.setDescription("Celular de ultima generacion Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image2.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct003() {
        Product product = new Product();
        Category category = createSubCategory001().orElseThrow();
        Brand brand = createBrand001().orElseThrow();

        product.setId(3L);
        product.setName("Notebook Lenovo");
        product.setDescription("Computador de ultima generacion Lenovo");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image3.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct004() {
        Product product = new Product();
        Category category = createSubCategory003().orElseThrow();
        Brand brand = createBrand003().orElseThrow();

        product.setId(4L);
        product.setName("Polera manga corta");
        product.setDescription("Polera manga corta de algodon");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image5.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static Optional<Product> createProduct005() {
        Product product = new Product();
        Category category = createSubCategory004().orElseThrow();
        Brand brand = createBrand003().orElseThrow();

        product.setId(5L);
        product.setName("Jeans Americanino");
        product.setDescription("Jeans Americanino hombre");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("image4.png");
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(product);
    }

    public static List<Category> createListOfCategories() {
        Category category1 = createCategory001().orElseThrow();
        Category category2 = createCategory002().orElseThrow();
        Category subCategory1 = createSubCategory001().orElseThrow();
        Category subCategory2 = createSubCategory002().orElseThrow();

        return List.of(category1, category2, subCategory1, subCategory2);
    }

    public static Optional<Category> createCategory001() {
        Category category = new Category(
            2L, 
            "Tecnología", 
            "Descripcion tecnología 2",
            null,
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createCategory002() {
        Category category = new Category(
            3L, 
            "Deportes", 
            "Descripcion deportes",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createCategory003() {
        Category category = new Category(
            4L, 
            "Ropa Hombre", 
            "Descripcion ropa hombre",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createUnknownCategory() {
        Category category = new Category(
            8L, 
            "Unknown", 
            "Unknown",
            null, 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory001() {
        Category category = new Category(
            4L, 
            "Computadoras", 
            "Descripcion computadoras",
            createCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory002() {
        Category category = new Category(
            5L, 
            "Celulares", 
            "Descripcion celulares",
            createCategory001().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory003() {
        Category category = new Category(
            6L, 
            "Poleras", 
            "Descripcion poleras",
            createCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static Optional<Category> createSubCategory004() {
        Category category = new Category(
            7L, 
            "Pantalones", 
            "Descripcion pantalones",
            createCategory003().orElseThrow(), 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(category);
    }

    public static List<Brand> createListOfBrands() {
        Brand brand1 = createBrand001().orElseThrow();
        Brand brand2 = createBrand002().orElseThrow();

        return List.of(brand1, brand2);
    }

    public static Optional<Brand> createBrand001() {
        Brand brand = new Brand(
            2L, 
            "Apple", 
            "Marca Apple", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand002() {
        Brand brand = new Brand(
            3L, 
            "Samsung", 
            "Marca Samsung", 
            "logo2.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Optional<Brand> createBrand003() {
        Brand brand = new Brand(
            4L, 
            "Americanino", 
            "Marca Americanino", 
            "logo5.png", 
            Timestamp.from(Instant.now()), 
            Timestamp.from(Instant.now())
        );

        return Optional.of(brand);
    }

    public static Product productCreated() {
        return createProduct001().orElseThrow();
    }

    public static List<ProductSku> createListOfProductSkus() {
        ProductSku productSku1 = createProductSku001().orElseThrow();
        ProductSku productSku2 = createProductSku002().orElseThrow();
        ProductSku productSku3 = createProductSku003().orElseThrow();
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku5 = createProductSku005().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();
        ProductSku productSku7 = createProductSku007().orElseThrow();
        
        return List.of(
            productSku1, productSku2, productSku3, productSku4, productSku5, productSku6, productSku7
        );
    }

    public static Optional<ProductSku> createProductSku001() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct001().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute003().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute001().orElseThrow();

        productSku.setId(1L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU001");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku002() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct002().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute003().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute001().orElseThrow();

        productSku.setId(2L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU002");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku003() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct003().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute003().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute001().orElseThrow();

        productSku.setId(3L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU003");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku004() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute001().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute001().orElseThrow();

        productSku.setId(4L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU004");
        productSku.setPrice(35.00);
        productSku.setStock(10);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku005() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute002().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute002().orElseThrow();

        productSku.setId(5L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU005");
        productSku.setPrice(25.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku006() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct004().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute002().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute002().orElseThrow();

        productSku.setId(6L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU006");
        productSku.setPrice(50.00);
        productSku.setStock(50);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductSku> createProductSku007() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct005().orElseThrow();
        ProductAttribute sizAttribute = createSizeAttribute001().orElseThrow();
        ProductAttribute colorAttribute = createColorAttribute001().orElseThrow();

        productSku.setId(7L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU007");
        productSku.setPrice(40.00);
        productSku.setStock(20);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(false);
        productSku.setCreatedAt(Timestamp.from(Instant.now()));
        productSku.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productSku);
    }

    public static Optional<ProductAttribute> createSizeAttribute001() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(1L);
        productAttribute.setType("size");
        productAttribute.setValue("S");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productAttribute);
    }

    public static Optional<ProductAttribute> createSizeAttribute002() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(2L);
        productAttribute.setType("size");
        productAttribute.setValue("M");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productAttribute);
    }

    public static Optional<ProductAttribute> createSizeAttribute003() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(3L);
        productAttribute.setType("size");
        productAttribute.setValue("none");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productAttribute);
    }

    public static Optional<ProductAttribute> createColorAttribute001() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(3L);
        productAttribute.setType("color");
        productAttribute.setValue("azul");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productAttribute);
    }

    public static Optional<ProductAttribute> createColorAttribute002() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setId(4L);
        productAttribute.setType("color");
        productAttribute.setValue("negro");
        productAttribute.setCreatedAt(Timestamp.from(Instant.now()));
        productAttribute.setUpdatedAt(Timestamp.from(Instant.now()));

        return Optional.of(productAttribute);
    }

    public static List<ProductAttribute> createListOfProductAttributes() {
        ProductAttribute productAttribute1 = createSizeAttribute001().orElseThrow();
        ProductAttribute productAttribute2 = createSizeAttribute002().orElseThrow();
        ProductAttribute productAttribute3 = createColorAttribute001().orElseThrow();
        ProductAttribute productAttribute4 = createColorAttribute001().orElseThrow();

        return List.of(productAttribute1, productAttribute2, productAttribute3, productAttribute4);
    }

    public static List<Map<String, Object>> createListOfProductsSkuMap() {
        ProductSku productSku1 = createProductSku001().orElseThrow();
        ProductSku productSku2 = createProductSku002().orElseThrow();
        ProductSku productSku3 = createProductSku003().orElseThrow();
        ProductSku productSku4 = createProductSku004().orElseThrow();
        ProductSku productSku5 = createProductSku005().orElseThrow();
        ProductSku productSku6 = createProductSku006().orElseThrow();
        ProductSku productSku7 = createProductSku007().orElseThrow();

        return createListOfMap(
            productSku1, productSku2, productSku3, productSku4, productSku5, productSku6, productSku7
        );
    }

    private static List<Map<String, Object>> createListOfMap(ProductSku...productSku) {
        List<Map<String, Object>> productSkusList = new ArrayList<>();
        for (int i = 0; i < productSku.length; i++) {
            Map<String, Object> productSkuMap = new HashMap<>();
                productSkuMap.put("objectID", productSku[i].getId());
                productSkuMap.put("size", productSku[i].getSizeAttribute().getValue());
                productSkuMap.put("color", productSku[i].getColorAttribute().getValue());
                productSkuMap.put("price", productSku[i].getPrice());
                productSkuMap.put("sku", productSku[i].getSku());
                productSkuMap.put("name", productSku[i].getProduct().getName());
                productSkuMap.put("description", productSku[i].getProduct().getDescription());
                productSkuMap.put("brand", productSku[i].getProduct().getBrand().getName());
                productSkuMap.put("brandId", productSku[i].getProduct().getBrand().getId());
                productSkuMap.put("category", productSku[i].getProduct().getCategory().getName());
                productSkuMap.put("categoryId", productSku[i].getProduct().getCategory().getId());
                productSkuMap.put("cover", productSku[i].getProduct().getCover());
            productSkusList.add(productSkuMap);
        }
        return productSkusList;

    }

    public static List<Product> createListOfProductsForSearch() {
        Product product1 = createProduct001().orElseThrow();
        Product product3 = createProduct003().orElseThrow();

        return List.of(product1, product3);
    }

    public static List<Product> createListOfFilterByCategory() {
        Product product2 = createProduct002().orElseThrow();
        Product product3 = createProduct003().orElseThrow();

        return List.of(product2, product3);
    }

    public static List<Product> createListOfFilterByBrand() {
        Product product1 = createProduct001().orElseThrow();
        Product product2 = createProduct002().orElseThrow();

        return List.of(product1, product2);
    }

    public static List<Product> createListOfFilterByPriceRange() {
        Product product1 = createProduct001().orElseThrow();
        Product product2 = createProduct002().orElseThrow();

        return List.of(product1, product2);
    }

}
