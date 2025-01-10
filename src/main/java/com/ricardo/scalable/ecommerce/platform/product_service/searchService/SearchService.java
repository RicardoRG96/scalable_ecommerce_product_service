package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

public interface SearchService {
    
    List<Product> searchProducts(String query);

}
