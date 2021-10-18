package com.codereviewtool.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codereviewtool.repo.SuggestionRepository;
import com.codereviewtool.repo.model.Suggestion;

@Service
public class SuggestionsService {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private BitbucketService bitbucketService;

    public List<Suggestion> getSuggestionsForPullRequest(String url){
        List<Suggestion> suggestionsList = new ArrayList();
        Optional<Set<String>> fileTypes = bitbucketService.getFileTypes(url);
        if(fileTypes.isPresent()){
            fileTypes.get().forEach(type -> suggestionsList.addAll(suggestionRepository.findSuggestionByType(type)));
        }
        return suggestionsList;
    }

}
