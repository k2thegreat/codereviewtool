package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Value{
    public int id;
    public Object createdDate;
    public User user;
    public String action;
    public String commentAction;
    public Comment comment;
    public CommentAnchor commentAnchor;
    public Diff diff;
}