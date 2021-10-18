package com.codereviewtool.common.model.bitbucket.pullrequests.diff;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Root{
    public List<Diff> diffs;
}
