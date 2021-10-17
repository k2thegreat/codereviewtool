package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Author{
    public String name;
    public String emailAddress;
}