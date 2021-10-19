package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Comment{
    public Properties properties;
    public int id;
    public int version;
    public String text;
    public Author author;
    public Object createdDate;
    public Object updatedDate;
    public List<Comment> comments;
}
