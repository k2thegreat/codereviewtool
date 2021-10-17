package com.codereviewtool.configuration;

import org.codehaus.jettison.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.common.util.RestTemplateUtil;
import com.codereviewtool.service.BatchProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;

@Configuration
public class Configurations {


    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

/*    public BatchProcessor getBatchProcessor(){
        return new BatchProcessor();
    }*/

    //    @Bean
    public MongoTemplate getMongoOperations() {
        MongoTemplate mongoTemplate = new MongoTemplate(MongoClients.create("mongodb+srv://reviewChecklistTool:reviewChecklistTool@cluster0.eredf.mongodb.net/db01?retryWrites=true&w=majority"), "db01");
        return mongoTemplate;
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RestTemplateUtil getRestTemplateUtil() {
        return new RestTemplateUtil();
    }

}
