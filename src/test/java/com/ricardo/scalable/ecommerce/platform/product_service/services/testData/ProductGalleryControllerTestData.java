package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Brand;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Category;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.Product;
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductAttribute;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductGallery;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.dto.ProductGalleryCreationDto;


public class ProductGalleryControllerTestData {

    // public static ProductGalleryCreationDto createProductGalleryCreationDto() throws IOException {
    //     ProductGalleryCreationDto productGallery = new ProductGalleryCreationDto();
    //     byte[] imageBytes = createSamplePNG();

    //     productGallery.setProductName("Polera Puma");
    //     productGallery.setColorName("red");
    //     MockMultipartFile file = 
    //         new MockMultipartFile(
    //             "file",
    //             "polera-puma.png",
    //             "image/png",
    //             imageBytes
    //         );
    //     productGallery.setImage(file);

    //     return productGallery;
    // }

    public static ProductGalleryCreationDto createProductGalleryCreationDtoOkResponse() throws IOException {
        ProductGalleryCreationDto productGallery = new ProductGalleryCreationDto();

        productGallery.setProductName("Polera Puma");
        productGallery.setColorName("red");
        Resource image = new ClassPathResource("ok-example.jpg");
        // MockMultipartFile file = 
        //     new MockMultipartFile(
        //         "file",
        //         "ok-example.jpg",
        //         "image/jpg",
        //         image.getInputStream()
        //     );
        MockMultipartFile file = 
            new MockMultipartFile(
                "file",
                image.getFilename(), 
                MediaType.IMAGE_JPEG_VALUE, 
                image.getInputStream()
            );
        productGallery.setImage(file);

        return productGallery;
    }

    public static ProductGallery createProductGallery() {
        ProductGallery productGallery = new ProductGallery();
        productGallery.setId(5L);
        productGallery.setProduct(createProduct());
        productGallery.setColorAttribute(createColorAttribute());
        productGallery.setImageUrl("https://example.com/images/jeans-lee-blue.jpg");

        return productGallery;
    }

    private static ProductAttribute createColorAttribute() {
        ProductAttribute colorAttribute = new ProductAttribute();

        colorAttribute.setId(2L);
        colorAttribute.setType("color");
        colorAttribute.setValue("blue");

        return colorAttribute;
    }

    private static Product createProduct() {
        Product product = new Product();
        Category category = createCategory();
        Brand brand = createBrand();

        product.setId(4L);
        product.setName("Jeans Lee");
        product.setDescription("Descripcion pantalones Lee");
        product.setCategory(category);
        product.setBrand(brand);
        product.setCover("https://example.com/images/jeans_lee.jpg");

        return product;
    }

    private static Category createCategory() {
        Category category = new Category();
        Category parenCategory = createParentCategory();

        category.setId(4L);
        category.setName("Jeans-hombre");
        category.setDescription("Descripcion jeans hombre");
        category.setParent(parenCategory);

        return category;
    }

    private static Category createParentCategory() {
        Category category = new Category();

        category.setId(1L);
        category.setName("Hombre");
        category.setDescription("Descripcion hombre");
        category.setParent(null);

        return category;
    }

    private static Brand createBrand() {
        Brand brand = new Brand();

        brand.setId(1L);
        brand.setName("Lee");
        brand.setDescription("Marca líder en moda");
        brand.setLogoUrl("https://example.com/lee_logo.png");

        return brand;
    }

}
