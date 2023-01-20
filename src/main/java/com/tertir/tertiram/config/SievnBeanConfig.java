package com.tertir.tertiram.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SievnBeanConfig {

    @Value("${sievn.url}")
    private String sievnURL;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
        return restTemplateBuilder
                .rootUri(sievnURL).messageConverters(new MappingJackson2HttpMessageConverter(),
                        new FormHttpMessageConverter(),
                        new ByteArrayHttpMessageConverter())
                .build();
    }

}
