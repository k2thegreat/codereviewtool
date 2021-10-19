package com.codereviewtool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    public List<Suggestion> getSuggestionsByType(String type) {
        List<Suggestion> suggestionsList = new ArrayList();
        suggestionsList.addAll(suggestionRepository.findSuggestionByType(type));
        return suggestionsList;
    }

    public Map<String,Integer> getSuggestionsCountByType() {
        Map<String, Integer> countMap = new HashMap<String, Integer>() {{
            put("UI_FORM", 0);
            put("UI_CONTROL", 0);
            put("HOME", 0);
            put("XML", 0);
            put("END_POINT", 0);
            put("SERVICE", 0);
            put("UTIL", 0);
            put("JS", 0);
            put("JSON", 0);
            put("CSS", 0);
            put("HTML", 0);
            put("OTHERS", 0);
        }};
        for (String type : countMap.keySet()) {
            List<Suggestion> suggestions = suggestionRepository.findSuggestionByType(type);
            if (suggestions != null && suggestions.size() > 0) {
                countMap.put(type, suggestions.size());
            }
        }
        return countMap;
    }

}
