package com.codereviewtool.common.model.bitbucket.pullrequests.diff;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Diff{
    public Source source;
}
