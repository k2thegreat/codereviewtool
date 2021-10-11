package com.codereviewtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.codereviewtool.configuration.Configurations;
import com.codereviewtool.controller.SearchController;
import com.codereviewtool.service.BatchProcessor;

@SpringBootApplication(scanBasePackageClasses = {SearchController.class, Configurations.class, BatchProcessor.class})
@EnableMongoRepositories
public class CodereviewtoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodereviewtoolApplication.class, args);
    }

}
