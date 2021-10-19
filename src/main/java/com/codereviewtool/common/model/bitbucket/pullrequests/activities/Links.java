package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Links{
    public List<Self> self;
}
