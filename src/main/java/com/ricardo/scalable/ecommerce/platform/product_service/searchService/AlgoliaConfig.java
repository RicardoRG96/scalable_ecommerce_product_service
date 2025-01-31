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
import com.algolia.model.search.IgnorePlurals;
import com.algolia.model.search.IndexSettings;
import com.algolia.model.search.RemoveStopWords;
import com.algolia.model.search.SupportedLanguage;
import com.algolia.model.search.SynonymHit;
import com.algolia.model.search.SynonymType;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.Product;
import com.ricardo.scalable.ecommerce.platform.product_service.entities.ProductSku;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductRepository;
import com.ricardo.scalable.ecommerce.platform.product_service.repositories.ProductSkuRepository;


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

    @Autowired
    private ProductSkuRepository productSkuRepository;

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
        }
        return searchClient;
    }

    private void setSettings(SearchClient searchClient) {
        searchClient.setSettings(
            indexName,
            new IndexSettings().setSearchableAttributes(Arrays.asList(
                "name",
                "description, brand, sku, category"
            )).setAttributesForFaceting(Arrays.asList(
                "brand",
                "category",
                "price",
                "size",
                "color"
            )).setCustomRanking(Arrays.asList(
                "asc(price)"
            )).setAttributesToRetrieve(Arrays.asList(
                "name",
                "description",
                "price",
                "sku",
                "size",
                "color",
                "brand",
                "brandId",
                "category",
                "categoryId",
                "cover"
            )).setHitsPerPage(28)
            .setQueryLanguages(Arrays.asList(SupportedLanguage.ES))
            .setRemoveStopWords(RemoveStopWords.of(true))
            .setIgnorePlurals(IgnorePlurals.of(true))
        );

        searchClient.saveSynonyms(
            indexName,
            Arrays.asList(
                new SynonymHit()
                    .setObjectID("7")
                    .setType(SynonymType.SYNONYM)
                    .setSynonyms(Arrays.asList(
                        "balon",
                        "pelota",
                        "bola",
                        "esferico"
                    )),
                new SynonymHit()
                    .setObjectID("52")
                    .setType(SynonymType.ONEWAYSYNONYM)
                    .setInput("iphone")
                    .setSynonyms(Arrays.asList("ephone", "aphone", "yphone"))
            ),
            true,
            true
        );
    }

    private Iterable<Map<String, Object>> preparedData() {
        List<Product> products = (List<Product>) getProducts();
 
        List<Map<String, Object>> preparedJson =  products.stream().map(product -> {
            Map<String, Object> productData = new HashMap<>();
            List<ProductSku> productSkus = (List<ProductSku>) productSkuRepository.findByProductId(product.getId());
            productSkus.stream().forEach(prodSku -> {
                productData.put("objectID", prodSku.getId());
                productData.put("size", prodSku.getSizeAttribute().getValue());
                productData.put("color", prodSku.getColorAttribute().getValue());
                productData.put("price", prodSku.getPrice());
                productData.put("sku", prodSku.getSku());
            });
            productData.put("name", product.getName());
            productData.put("description", product.getDescription());
            productData.put("brand", product.getBrand().getName());
            productData.put("brandId", product.getBrand().getId());
            productData.put("category", product.getCategory().getName());
            productData.put("categoryId", product.getCategory().getId());
            productData.put("cover", product.getCover());
            return productData;
        }).collect(Collectors.toList());

        return (Iterable<Map<String, Object>>) preparedJson;
    }

    private Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

}
