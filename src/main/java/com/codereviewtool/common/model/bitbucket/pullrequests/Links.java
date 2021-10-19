package com.codereviewtool.common.model.bitbucket.pullrequests;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Links{
    List<Self> self;
}
