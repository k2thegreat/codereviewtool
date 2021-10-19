package com.codereviewtool;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableBatchProcessing
public class CodeReviewToolApplication {

  public static void main(String[] args) {
    SpringApplication.run(CodeReviewToolApplication.class, args);
  }
}
