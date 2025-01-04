package com.ricardo.scalable.ecommerce.platform.product_service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.algolia.api.SearchClient;

@Configuration
public class AlgoliaConfig {

    @Value("${algolia.applicationId}")
    private String applicationId;

    @Value("${algolia.apiKey}")
    private String apiKey;

    @Bean
    SearchClient searchClient() {
        return new SearchClient(applicationId, apiKey);
    }

}
