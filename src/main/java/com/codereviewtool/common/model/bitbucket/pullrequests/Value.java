package com.codereviewtool.common.model.bitbucket.pullrequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Value{
    private int id;
    private String state;
    private boolean closed;
    private String createdDate;
    private String closedDate;
    private Links links;
}