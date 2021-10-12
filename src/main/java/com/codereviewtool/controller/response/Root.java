package com.codereviewtool.controller.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Root{
    public List<Suggestion> suggestions;
}