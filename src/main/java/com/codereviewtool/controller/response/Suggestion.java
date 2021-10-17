package com.codereviewtool.controller.response;

import java.util.List;

import lombok.Data;

@Data
public class Suggestion{
    private String type;
    private List<Review> reviews;
}
