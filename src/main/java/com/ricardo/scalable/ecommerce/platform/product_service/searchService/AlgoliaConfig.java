package com.ricardo.scalable.ecommerce.platform.product_service.searchService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algolia.api.SearchClient;
import com.algolia.model.search.IndexSettings;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;


@Configuration
public class AlgoliaConfig {

    @Value("${algolia.applicationId}")
    private String applicationId;

    @Value("${algolia.apiKey}")
    private String apiKey;

    @Value("${algolia.indexName}")
    private String indexName;

    @Autowired
    private ProductRepository productRepository;


    @Bean
    SearchClient searchClient() {
        SearchClient searchClient = null;
        try {
            searchClient = new SearchClient(applicationId, apiKey);
            setSettings(searchClient);
            Iterable<Map<String, Object>> data = preparedData();
            searchClient.saveObjects(indexName, data);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (searchClient != null) {
                try {
                    searchClient.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return searchClient;
    }

    private void setSettings(SearchClient searchClient) {
        searchClient.setSettings(
            indexName,
            new IndexSettings().setSearchableAttributes(Arrays.asList(
                "name",
                "description, brand, sku, upc, category"
            )).setAttributesForFaceting(Arrays.asList(
                "brand",
                "categories",
                "price"
            )).setAttributesToRetrieve(Arrays.asList(
                "name",
                "description",
                "price",
                "sku",
                "upc",
                "brand",
                "categories",
                "imageUrl"
            )).setHitsPerPage(28)
        );
    }

    private Iterable<Map<String, Object>> preparedData() {
        List<Product> products = (List<Product>) getProducts();
 
        List<Map<String, Object>> preparedJson =  products.stream().map(product -> {
            Map<String, Object> productData = new HashMap<>();
            productData.put("objectID", product.getId());
            productData.put("name", product.getName());
            productData.put("description", product.getDescription());
            productData.put("price", product.getPrice());
            productData.put("sku", product.getSku());
            productData.put("upc", product.getUpc());
            productData.put("brand", product.getBrand().getName());
            productData.put("categories", product.getCategory().getName());
            productData.put("imageUrl", product.getImageUrl());
            return productData;
        }).collect(Collectors.toList());

        return (Iterable<Map<String, Object>>) preparedJson;
    }

    private Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

}
