package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algolia.api.SearchClient;
import com.algolia.model.search.SearchParamsObject;
import com.algolia.model.search.SearchResponse;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;

@Service
public class AlgoliaSearchServiceImpl implements SearchService {

    @Autowired
    private final SearchClient searchClient;

    public AlgoliaSearchServiceImpl(SearchClient searchClient) {
        this.searchClient = searchClient;
    }

    public List<Product> searchProducts(String query) {
        SearchResponse<Product> searchResults = searchClient.searchSingleIndex(
            query,
            new SearchParamsObject().setQuery(query),
            Product.class
        );

        return searchResults
                .getHits()
                .stream()
                .collect(Collectors.toList());
    }

}
