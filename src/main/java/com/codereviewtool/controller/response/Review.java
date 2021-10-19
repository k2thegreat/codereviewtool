package com.codereviewtool.controller.response;

import lombok.Data;

@Data
public class Review{
    private String comment;
    private String reviewer;
    private String pull_request_link;
    private Props props;
}