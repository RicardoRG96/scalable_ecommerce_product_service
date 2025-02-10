package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import java.util.List;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.*;

public class SearchServiceClientTestData {

    public static List<ProductSku> createListOfProductsForSearch() {
        ProductSku product1 = createProductSku001().orElseThrow();
        ProductSku product3 = createProductSku003().orElseThrow();

        return List.of(product1, product3);
    }

    public static List<ProductSku> createListOfFilterByCategory() {
        ProductSku product1 = createProductSku001().orElseThrow();
        ProductSku product3 = createProductSku003().orElseThrow();

        return List.of(product1, product3);
    }

    public static List<ProductSku> createListOfFilterByBrand() {
        ProductSku product2 = createProductSku002().orElseThrow();
        ProductSku product3 = createProductSku003().orElseThrow();

        return List.of(product2, product3);
    }

    public static List<ProductSku> createListOfFilterByPriceRange() {
        ProductSku product1 = createProductSku001().orElseThrow();
        ProductSku product3 = createProductSku003().orElseThrow();

        return List.of(product1, product3);
    }

}
