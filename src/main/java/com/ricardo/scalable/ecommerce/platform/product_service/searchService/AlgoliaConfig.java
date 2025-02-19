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
import com.ricardo.scalable.ecommerce.platform.libs_common.entities.ProductSku;
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
        List<ProductSku> productsSku = (List<ProductSku>) getProductsSku();
 
        List<Map<String, Object>> preparedJson =  productsSku.stream().map(productSku -> {
            Map<String, Object> productData = new HashMap<>();
            Long productId = productSku.getProduct().getId();
            String productName = productRepository.findById(productId).orElseThrow().getName();
            String productDescription = productRepository.findById(productId).orElseThrow().getDescription();
            String productBrand = productRepository.findById(productId).orElseThrow().getBrand().getName();
            String productCategory = productRepository.findById(productId).orElseThrow().getCategory().getName();
            Long productBrandId = productRepository.findById(productId).orElseThrow().getBrand().getId();
            Long productCategoryId = productRepository.findById(productId).orElseThrow().getCategory().getId();
            String productCover = productRepository.findById(productId).orElseThrow().getCover();

            productData.put("objectID", productSku.getId());
            productData.put("size", productSku.getSizeAttribute().getValue());
            productData.put("color", productSku.getColorAttribute().getValue());
            productData.put("price", productSku.getPrice());
            productData.put("sku", productSku.getSku());
            productData.put("name", productName);
            productData.put("description", productDescription);
            productData.put("brand", productBrand);
            productData.put("brandId", productBrandId);
            productData.put("category", productCategory);
            productData.put("categoryId", productCategoryId);
            productData.put("cover", productCover);
            return productData;
        }).collect(Collectors.toList());

        return (Iterable<Map<String, Object>>) preparedJson;
    }

    private Iterable<ProductSku> getProductsSku() {
        return productSkuRepository.findAll();
    }

}
