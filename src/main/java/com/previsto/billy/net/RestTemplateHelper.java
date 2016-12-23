package com.previsto.billy.net;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestTemplateHelper {

    private RestTemplateHelper() {}
    
    public static void configureForBillyJargon(RestTemplate restTemplate) {
        // Changed converter defaults
        for(HttpMessageConverter converter : restTemplate.getMessageConverters()) {
            if(converter instanceof MappingJackson2HttpMessageConverter) {
                ObjectMapper om = ((MappingJackson2HttpMessageConverter)converter).getObjectMapper();
                om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                om.enable(SerializationFeature.INDENT_OUTPUT);
            }
        }
        
        // Apply paged result interceptor
        restTemplate.getInterceptors().add(new ResultInterceptor());
    }
}