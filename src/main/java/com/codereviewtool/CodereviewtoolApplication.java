package com.codereviewtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.codereviewtool.controller.SearchController;

@SpringBootApplication(scanBasePackageClasses = {SearchController.class})
@EnableMongoRepositories
public class CodereviewtoolApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodereviewtoolApplication.class, args);
    }

}
