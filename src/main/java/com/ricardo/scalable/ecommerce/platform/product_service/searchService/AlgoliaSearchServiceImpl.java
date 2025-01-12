package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.algolia.api.SearchClient;
import com.algolia.model.search.Hit;
import com.algolia.model.search.SearchParamsObject;
import com.algolia.model.search.SearchResponse;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.services.ProductService;

@Service
public class AlgoliaSearchServiceImpl implements SearchService {

    @Autowired
    private final SearchClient searchClient;

    @Autowired
    private ProductService productService;

    @Value("${algolia.indexName}")
    private String indexName;

    public AlgoliaSearchServiceImpl(SearchClient searchClient) {
        this.searchClient = searchClient;
    }

    public List<Product> searchProducts(String query) {
        SearchResponse<Hit> searchResults = searchClient.searchSingleIndex(
            indexName,
            new SearchParamsObject().setQuery(query),
            Hit.class
        );
        
        List<Product> searchedProductMatches = searchResults
                .getHits()
                .stream()
                .map(hit -> productService.findById(Long.parseLong(hit.getObjectID())).orElseThrow())
                .collect(Collectors.toList());

        
        return searchedProductMatches;
    }

    public List<Product> filterByBrand(String brand) {
        SearchResponse<Product> searchResults = searchClient.searchSingleIndex(
            indexName,
            new SearchParamsObject().setQuery(brand).setFilters("brand:" + brand),
            Product.class
        );

        return searchResults
                .getHits()
                .stream()
                .collect(Collectors.toList());
    }

    public List<Product> filterByCategory(String category) {
        SearchResponse<Product> searchResults = searchClient.searchSingleIndex(
            indexName,
            new SearchParamsObject().setQuery(category).setFilters("category:" + category),
            Product.class
        );

        return searchResults
                .getHits()
                .stream()
                .collect(Collectors.toList());
    }

}
