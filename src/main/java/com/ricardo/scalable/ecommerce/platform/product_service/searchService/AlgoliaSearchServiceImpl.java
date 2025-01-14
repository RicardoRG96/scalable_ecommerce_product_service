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

    @Override
    public List<Product> searchProducts(String query) {
        SearchResponse<Hit> searchResults = searchClient.searchSingleIndex(
            indexName,
            new SearchParamsObject().setQuery(query),
            Hit.class
        );
        
        List<Product> searchedProductMatches = getProductsFromSearchResults(searchResults);

        return searchedProductMatches;
    }

    private List<Product> getProductsFromSearchResults(SearchResponse<Hit> searchResults) {
        return searchResults
                .getHits()
                .stream()
                .map(hit -> productService.findById(Long.parseLong(hit.getObjectID())).orElseThrow())
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> filterByBrand(List<String> brand) {
        List<SearchResponse<Hit>> searchResponses = brand
                .stream()
                .map(b -> {
                    SearchResponse<Hit> searchResults = searchClient.searchSingleIndex(
                        indexName,
                        new SearchParamsObject().setQuery(b).setFilters("brand:" + b),
                        Hit.class
                    );
                    return searchResults;
                })
                .collect(Collectors.toList());

        List<Product> searchedProductMatches = searchResponses
                .stream()
                .map(this::getProductsFromSearchResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return searchedProductMatches;
    }

    @Override
    public List<Product> filterByCategory(List<String> category) {
        List<SearchResponse<Hit>> searchResponses = category
                .stream()
                .map(c -> {
                    SearchResponse<Hit> searchResults = searchClient.searchSingleIndex(
                        indexName,
                        new SearchParamsObject().setQuery(c).setFilters("category:" + c),
                        Hit.class
                    );
                    return searchResults;
                })
                .collect(Collectors.toList());

        List<Product> searchedProductMatches = searchResponses
                .stream()
                .map(this::getProductsFromSearchResults)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        return searchedProductMatches;
    }

}
