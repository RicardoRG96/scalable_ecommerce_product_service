package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

@Service
public class SearchServiceClientImpl implements SearchServiceClient {

    @Autowired
    private final SearchService searchService;

    public SearchServiceClientImpl(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<Product> searchProducts(String query) {
        return searchService.searchProducts(query);
    }

    @Override
    public List<Product> filterByBrand(List<String> brand) {
        return searchService.filterByBrand(brand);
    }

    @Override
    public List<Product> filterByCategory(List<String> category) {
        return searchService.filterByCategory(category);
    }

    @Override
    public List<Product> filterByPriceRange(Double minPrice, Double maxPrice) {
        return searchService.filterByPriceRange(minPrice, maxPrice);
    }

}
