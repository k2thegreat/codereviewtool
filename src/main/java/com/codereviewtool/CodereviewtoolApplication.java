package com.codereviewtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.codereviewtool.configuration.Configurations;
import com.codereviewtool.controller.SuggestionsController;
import com.codereviewtool.service.BatchProcessor;

@SpringBootApplication(scanBasePackageClasses = {SuggestionsController.class, Configurations.class, BatchProcessor.class})
@EnableMongoRepositories
public class CodereviewtoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodereviewtoolApplication.class, args);
    }

}
