package com.codereviewtool.common.model.bitbucket.pullrequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Author{
    private User user;
}
