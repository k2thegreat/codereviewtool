package com.codereviewtool.controller.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Review{
    public String comment;
    public String reviewer;
    public String pull_request_link;
    public Props props;
}