package com.codereviewtool.common.model.bitbucket.pullrequests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Root{
    private int size;
    private int limit;
    private boolean isLastPage;
    private List<Value> values;
    private int start;
    private int nextPageStart;
}
