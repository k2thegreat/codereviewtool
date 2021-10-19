package com.codereviewtool.controller.response;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Suggestion {
  public String type;
  public List<Comment> comments;
  public String fileName;
  public String codeSnippet;
  public String reviewer;
  public String pullRequestLink;
  public String date;
  public Props props;
}
