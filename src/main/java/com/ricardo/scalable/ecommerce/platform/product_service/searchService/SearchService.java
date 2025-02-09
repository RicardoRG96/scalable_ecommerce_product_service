package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;

public interface SearchService {
    
    List<ProductSku> searchProducts(String query);

    List<ProductSku> filterByBrand(List<String> brand);

    List<ProductSku> filterByCategory(List<String> category);

    List<ProductSku> filterByPriceRange(Double minPrice, Double maxPrice);

}
