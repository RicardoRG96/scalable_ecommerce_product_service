package com.ricardo.scalable.ecommerce.platform.product_service.services.testData;

import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku001;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku002;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku003;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku004;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku005;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku006;
import static com.ricardo.scalable.ecommerce.platform.product_service.services.testData.ProductSkuServiceTestData.createProductSku007;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;

public class AlgoliaConfigTestData {

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

}
