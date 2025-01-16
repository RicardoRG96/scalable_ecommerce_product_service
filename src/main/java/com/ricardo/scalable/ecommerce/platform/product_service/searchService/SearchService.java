package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public interface SearchService {
    
    List<Product> searchProducts(String query);

    List<Product> filterByBrand(List<String> brand);

    List<Product> filterByCategory(List<String> category);

    List<Product> filterByPriceRange(Double minPrice, Double maxPrice);

}
