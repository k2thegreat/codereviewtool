package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Root{
    public int pullRequestId;
    public int size;
    public int limit;
    public boolean isLastPage;
    public List<Value> values;
    public int start;
    public int nextPageStart;
}
