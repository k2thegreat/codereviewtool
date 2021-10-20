package com.codereviewtool.configuration;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.codereviewtool.common.model.bitbucket.pullrequests.ClassType;
import com.codereviewtool.common.model.bitbucket.pullrequests.activities.Diff;
import com.codereviewtool.common.model.bitbucket.pullrequests.activities.Hunk;
import com.codereviewtool.common.model.bitbucket.pullrequests.activities.Line;
import com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root;
import com.codereviewtool.common.model.bitbucket.pullrequests.activities.Segment;
import com.codereviewtool.controller.response.Comment;
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

  @Value("${codereviewtool.bitbucket.pullrequestdiffurl}")
  private String pullRequestsDiffURL;

  @Bean
  ItemReader<List<Suggestion>> reader() {
    return new ItemReader<List<Suggestion>>() {
      @Override
      public List<Suggestion> read() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        List<Suggestion> suggestions = new ArrayList<>();
        List<Root> roots = mapper.readValue(jsonReader(), new TypeReference<List<Root>>() {});
        mapSuggestions(suggestions, roots);
        return suggestions;
      }
    };
  }

  private void mapSuggestions(List<Suggestion> suggestions, List<Root> roots) {
    for (Root root : roots) {
      int pullRequestId = root.getPullRequestId();
      for (com.codereviewtool.common.model.bitbucket.pullrequests.activities.Value value :
          root.values) {
        if (!(value.action.equals("COMMENTED") && value.commentAction.equals("ADDED"))) {
          continue;
        }
        Suggestion suggestion = new Suggestion();
        suggestion.setReviewer(
            value.comment != null
                ? (value.comment.author != null ? value.comment.author.getName() : "")
                : "");
        suggestion.setDate(String.valueOf(value.getCreatedDate()));
        suggestion.setFileName(value.commentAnchor != null ? value.commentAnchor.getPath() : "");
        suggestion.setType(
            getFileType(value.commentAnchor != null ? value.commentAnchor.getPath() : ""));
        suggestion.setProps(null);
        suggestion.setPullRequestLink(
            pullRequestsDiffURL.replace("REQ_ID", String.valueOf(pullRequestId)));
        suggestion.setComments(mapComments(value.comment));
        suggestion.setCodeSnippet(mapCodeSnippet(value.diff));
        suggestions.add(suggestion);
      }
    }
  }

  private String mapCodeSnippet(Diff diff) {
    if (diff == null) return "";

    for (Hunk hunk : diff.getHunks()) {
      for (Segment segment : hunk.getSegments()) {
        for (Line line : segment.getLines()) {
          if (line.getCommentIds() != null) {
            int id = line.commentIds.stream().filter(Objects::nonNull).findFirst().orElse(0);
            if (id != 0) {
              return line.getLine();
            }
          }
        }
      }
    }
    return "";
  }

  private List<Comment> mapComments(
      com.codereviewtool.common.model.bitbucket.pullrequests.activities.Comment comment) {
    if (comment == null) return Collections.emptyList();
    List<Comment> commentList = new ArrayList<>();
    Comment firstComment = new Comment();
    firstComment.setComment(comment.getText());
    firstComment.setAuthor(comment.getAuthor() != null ? comment.getAuthor().getName() : "");
    //    for (com.codereviewtool.common.model.bitbucket.pullrequests.activities.Comment
    // otherComments :
    //        comment.getComments()) {
    //      Comment comment1 = new Comment();
    //      comment1.setAuthor(otherComments.getAuthor().getName());
    //      comment1.setComment(otherComments.getText());
    //      commentList.add(comment1);
    //    }
    Queue<com.codereviewtool.common.model.bitbucket.pullrequests.activities.Comment> queue =
        new ArrayDeque<>();
    if (comment.getComments() != null) {
      queue.addAll(comment.getComments());
      while (!queue.isEmpty()) {
        com.codereviewtool.common.model.bitbucket.pullrequests.activities.Comment comment1 =
            queue.poll();
        Comment responseComment = new Comment();
        responseComment.setAuthor(
            comment1.getAuthor() != null ? comment1.getAuthor().getName() : "");
        responseComment.setComment(comment1.getText());
        commentList.add(responseComment);
        if (comment1.getComments() != null) {
          queue.addAll(comment1.getComments());
        }
      }
    }

    return commentList;
  }

  private String getFileType(String path) {
    for (ClassType classType : ClassType.values()) {
      if (path.toLowerCase().contains(classType.toString().toLowerCase())) {
        return classType.toString();
      }
    }
    return ClassType.OTHERS.toString();
  }

  @Bean
  ReviewCommentProcessor processor() {
    return new ReviewCommentProcessor();
  }

  String jsonReader() throws IOException {
    return new String(Files.readAllBytes(Paths.get("src/main/resources/AmitData.json")));
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
