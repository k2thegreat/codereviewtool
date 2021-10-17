package com.codereviewtool.common.model.bitbucket.pullrequests.activities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown=true)
public class Segment{
    public String type;
    public List<Line> lines;
    public boolean truncated;
}