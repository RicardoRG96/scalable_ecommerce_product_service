package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Category;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductSkuCreationDto;

public class ProductSkuControllerTestData {

    public static ProductSkuCreationDto createProductSkuCreationDto() {
        ProductSkuCreationDto productSku = new ProductSkuCreationDto();
        productSku.setProductId(4L);
        productSku.setSizeAttributeId(5L);
        productSku.setColorAttributeId(2L);
        productSku.setSku("SKU100496");
        productSku.setPrice(9.99);
        productSku.setStock(100);
        productSku.setIsActive(true);
        productSku.setIsFeatured(false);
        productSku.setIsOnSale(true);

        return productSku;
    }

    public static ProductSku createProductSku() {
        ProductSku productSku = new ProductSku();
        Product product = createProduct();
        ProductAttribute sizeAttribute = createSizeAttribute();
        ProductAttribute colorAttribute = createColorAttribute();

        productSku.setId(1L);
        productSku.setProduct(product);
        productSku.setSizeAttribute(sizeAttribute);
        productSku.setColorAttribute(colorAttribute);
        productSku.setSku("SKU2210");
        productSku.setPrice(1500.99);
        productSku.setStock(540);
        productSku.setIsActive(true);
        productSku.setIsFeatured(true);
        productSku.setIsOnSale(true);

        return productSku;
    }

    private static ProductAttribute createSizeAttribute() {
        ProductAttribute sizeAttribute = new ProductAttribute();

        sizeAttribute.setId(8L);
        sizeAttribute.setType("size");
        sizeAttribute.setValue("none-size");
        
        return sizeAttribute;
    }

    private static ProductAttribute createColorAttribute() {
        ProductAttribute colorAttribute = new ProductAttribute();

        colorAttribute.setId(3L);
        colorAttribute.setType("color");
        colorAttribute.setValue("black");

        return colorAttribute;
    }

    private static Product createProduct() {
        Product product = new Product();
        Category category = createCategory();
        Brand brand = createBrand();

        product.setId(1L);
        product.setName("iPhone 15");
        product.setDescription("Smartphone Apple");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("https://example.com/images/iphone15.jpg");

        return product;
    }

    private static Category createCategory() {
        Category category = new Category();
        Category parenCategory = createParentCategory();

        category.setId(6L);
        category.setName("Smartphone");
        category.setDescription("Telefonos celulares");
        category.setParent(parenCategory);

        return category;
    }

    private static Category createParentCategory() {
        Category category = new Category();

        category.setId(2L);
        category.setName("Tecnologia");
        category.setDescription("Descripcion tecnologia");
        category.setParent(null);

        return category;
    }

    private static Brand createBrand() {
        Brand brand = new Brand();

        brand.setId(2L);
        brand.setName("Apple");
        brand.setDescription("Marca líder en tecnologia");
        brand.setLogoUrl("https://example.com/apple_logo.png");

        return brand;
    }

}
