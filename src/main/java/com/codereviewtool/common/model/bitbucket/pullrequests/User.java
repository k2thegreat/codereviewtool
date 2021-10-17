package com.codereviewtool.common.model.bitbucket.pullrequests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class User{
    public String name;
    public String emailAddress;
    public int id;
}