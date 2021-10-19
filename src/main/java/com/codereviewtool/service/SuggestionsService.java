package com.codereviewtool.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codereviewtool.repo.SuggestionRepository;
import com.codereviewtool.repo.model.Suggestion;

@Service
public class SuggestionsService {

    @Autowired
    private SuggestionRepository suggestionRepository;

    @Autowired
    private BitbucketService bitbucketService;

    public List<Suggestion> getSuggestionsForPullRequest(String url,String page,String size) {
        List<Suggestion> suggestionsList = new ArrayList();
        Optional<Set<String>> fileTypes = bitbucketService.getFileTypes(url);
        Pageable pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        if (fileTypes.isPresent()) {
            fileTypes.get().forEach(type -> suggestionsList.addAll(suggestionRepository.findSuggestionByType(type, pageable).getContent()));
        }
        return suggestionsList;
    }

    public List<Suggestion> getSuggestionsByType(String type,String page,String size) {
        List<Suggestion> suggestionsList = new ArrayList();
        Pageable pageable = PageRequest.of(Integer.valueOf(page), Integer.valueOf(size));
        suggestionsList.addAll(suggestionRepository.findSuggestionByType(type,pageable).getContent());
        return suggestionsList;
    }

    public Map<String, Long> getSuggestionsCountByType() {
        Map<String, Long> countMap = new HashMap<String, Long>() {{
            put("UI_FORM", 0L);
            put("UI_CONTROL", 0L);
            put("HOME", 0L);
            put("XML", 0L);
            put("END_POINT", 0L);
            put("SERVICE", 0L);
            put("UTIL", 0L);
            put("JS", 0L);
            put("JSON", 0L);
            put("CSS", 0L);
            put("HTML", 0L);
            put("OTHERS", 0L);
        }};
        for (String type : countMap.keySet()) {
            Pageable pageable = PageRequest.of(1, 1);
            Page<Suggestion> pageSuggestion = suggestionRepository.findSuggestionByType(type, pageable);
            List<Suggestion> suggestions = pageSuggestion.getContent();
            if (suggestions != null && pageSuggestion.getTotalElements() > 0) {
                countMap.put(type, pageSuggestion.getTotalElements());
            }
        }
        return countMap;
    }

}
