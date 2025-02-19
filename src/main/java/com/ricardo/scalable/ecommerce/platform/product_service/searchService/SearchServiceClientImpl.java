package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;

@Service
public class SearchServiceClientImpl implements SearchServiceClient {

    @Autowired
    private final SearchService searchService;

    public SearchServiceClientImpl(SearchService searchService) {
        this.searchService = searchService;
    }

    @Override
    public List<ProductSku> searchProducts(String query) {
        return searchService.searchProducts(query);
    }

    @Override
    public List<ProductSku> filterByBrand(List<String> brand) {
        return searchService.filterByBrand(brand);
    }

    @Override
    public List<ProductSku> filterByCategory(List<String> category) {
        return searchService.filterByCategory(category);
    }

    @Override
    public List<ProductSku> filterByPriceRange(Double minPrice, Double maxPrice) {
        return searchService.filterByPriceRange(minPrice, maxPrice);
    }

}
