package com.codereviewtool.repo.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Document("Suggestions")
public class Suggestion {
  @Id
  private String id;
  public String type;
  public List<Comment> comments;
  public String fileName;
  public String codeSnippet;
  public String reviewer;
  public String pullRequestLink;
  public String date;
  public Props props;

  public Suggestion(String id, String type, List<Comment> comments, String fileName, String codeSnippet, String reviewer, String pullRequestLink, String date, Props props) {
    super();
    this.id = id;
    this.type = type;
    this.comments = comments;
    this.fileName = fileName;
    this.codeSnippet = codeSnippet;
    this.reviewer = reviewer;
    this.pullRequestLink = pullRequestLink;
    this.date = date;
    this.props = props;
  }
}
