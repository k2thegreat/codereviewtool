package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Properties{
    @JsonProperty("jira-key")
    public List<String> jiraKey;
    public int repositoryId;
    public String toHash;
    public boolean current;
    public String fromHash;
}
