package com.codereviewtool.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.controller.response.Suggestion;
import com.codereviewtool.processor.ReviewCommentProcessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class BatchConfiguration {
  private static final Logger LOG = LoggerFactory.getLogger(BatchConfiguration.class);

  @Autowired JobBuilderFactory jobBuilderFactory;
  @Autowired StepBuilderFactory stepBuilderFactory;
  @Autowired MongoTemplate mongoTemplate;

  @Bean
  ItemReader<List<Suggestion>> reader() {
    return new ItemReader<List<Suggestion>>() {
      @Override
      public List<Suggestion> read() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(jsonReader(), new TypeReference<List<Suggestion>>() {});
      }
    };
  }

  @Bean
  ReviewCommentProcessor processor() {
    return new ReviewCommentProcessor();
  }

  String jsonReader() throws IOException {
    return new String(Files.readAllBytes(Paths.get("src/main/resources/Sample.json")));
  }

  @Bean
  public ItemWriter<List<Document>> writer() {
    return new ItemWriter<List<Document>>() {
      @Override
      public void write(List<? extends List<Document>> items) throws Exception {
        mongoTemplate.insert(items.get(0), "Suggestions");
      }
    };
  }

  @Bean
  public Job runJob() {
    Step step =
            stepBuilderFactory
                    .get("step1")
                    .<List<Suggestion>, List<Document>>chunk(2)
                    .reader(reader())
                    .processor(processor())
                    .writer(writer())
                    .build();

    return jobBuilderFactory
            .get("Bulk operation")
            .incrementer(new RunIdIncrementer())
            .start(step)
            .build();
  }

  //  @Scheduled(cron = "30 23 23 * * *")
  //  public void startScandvPrayTimeJob() throws Exception {
  //    Document batchInfos = new Document();
  //    batchInfos.put("name", "SCANDINAVIAN_PRAY_TIME_BATCH");
  //    LOG.info(" ====> Job Started at :" + new Date());
  //    JobParameters param =
  //        new JobParametersBuilder()
  //            .addString("JobID", String.valueOf(System.currentTimeMillis()))
  //            .toJobParameters();
  //    JobExecution execution = jobLauncher.run(scandvPrayTimeJob(), param);
  //    LOG.info(" ====> Job finished with status : " + execution.getStatus());
  //    batchInfos.put("status", execution.getStatus().name());
  //    batchInfos.put("end_time", execution.getEndTime());
  //    batchInfos.put("id", execution.getId());
  //    batchInfos.put("failure_exceptions", execution.getFailureExceptions().toString());
  //    batchInfos.put("start_time", execution.getStartTime());
  //    batchInfos.put("version", execution.getVersion());
  //    // cloudMongoService.insertOne("batch_history", batchInfos);
  //  }
}
