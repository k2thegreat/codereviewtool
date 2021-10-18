package com.codereviewtool.processor;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.codereviewtool.controller.response.Comment;
import com.codereviewtool.controller.response.Suggestion;

public class ReviewCommentProcessor implements ItemProcessor<List<Suggestion>, List<Document>> {
  private static final Logger log = LoggerFactory.getLogger(ReviewCommentProcessor.class);
  @Autowired MongoTemplate mongoTemplate;

  @Override
  public List<Document> process(List<Suggestion> item) throws Exception {
    log.info("processing review comment data.....{}", item);
    List<Document> docs = new ArrayList<>();
    for (Suggestion suggestion : item) {
      Document document = new Document();

      document.put("type", suggestion.getType());
      document.put("reviewer", suggestion.getReviewer());
      document.put("pullRequestLink", suggestion.getPullRequestLink());
      document.put("date", suggestion.getDate());
      List<Document> commentDocument = new ArrayList<>();
      for (Comment comment : suggestion.getComments()) {
        Document document1 = new Document();
        document1.put("comment", comment.getComment());
        document1.put("author", comment.getAuthor());
        commentDocument.add(document1);
      }
      document.put("comments", commentDocument);
      document.put("fileName", suggestion.getFileName());
      document.put("codeSnippet", suggestion.getCodeSnippet());
      document.put("props", suggestion.getProps());
      docs.add(document);
    }
    return docs;
  }
}
