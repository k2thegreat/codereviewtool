package com.codereviewtool.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.service.BatchProcessor;

@Configuration
public class Configurations {


    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

/*    public BatchProcessor getBatchProcessor(){
        return new BatchProcessor();
    }*/

}
