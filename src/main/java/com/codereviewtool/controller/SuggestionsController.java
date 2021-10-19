package com.codereviewtool.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codereviewtool.controller.response.Root;
import com.codereviewtool.controller.response.Suggestion;
import com.codereviewtool.service.BatchProcessor;
import com.codereviewtool.service.BitbucketService;
import com.codereviewtool.service.SuggestionsService;

@RestController
@RequestMapping("/suggestions")
public class SuggestionsController {

    /*@Autowired
    ItemRepository groceryItemRepo;*/

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    BatchProcessor batchProcessor;

    @Autowired
    BitbucketService bitbucketService;

    @Autowired
    private SuggestionsService suggestionsService;

    @GetMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<com.codereviewtool.repo.model.Suggestion> getSuggestionsByPullRequest(@RequestParam(value = "url",required = true) String url) {

        return suggestionsService.getSuggestionsForPullRequest(url);
    }

    @GetMapping(value = "/{type}",produces = MediaType.APPLICATION_JSON_VALUE)
    public Root getSuggestionsByType(@PathVariable(value = "type",required = true) String type) {

        Suggestion suggestion = new Suggestion();
        suggestion.setType(type);
        suggestion.setComments(new ArrayList<>());
        Root root = new Root();
        List<Suggestion> suggestions = new ArrayList();
        suggestions.add(suggestion);
        root.setSuggestions(suggestions);
        return root;
    }

    @GetMapping(value = "/bitbucket", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<com.codereviewtool.common.model.bitbucket.pullrequests.Root> getBitbucketPullRequests(@RequestParam(value = "limit", required = true) String limit, @RequestParam(value = "start", required = true) String start, @RequestParam(value = "slug", required = true) String slug) {


        return bitbucketService.getPullRequests(limit, start, slug).get();
    }

  @GetMapping(value = "/bitbucket/comments", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root>
      getBitbucketPullRequestsActivities(
          @RequestParam(value = "limit", required = true) String limit,
          @RequestParam(value = "start", required = true) String start,
          @RequestParam(value = "slug", required = true) String slug) {

    return bitbucketService.getPullRequestsActivities(limit, start, slug).get();
  }

    @PostMapping(value = "/createsample")
    public void createGroceryItems(){
            System.out.println("Data creation started...");
            /*groceryItemRepo.save(new items("Whole Wheat Biscuit", "Whole Wheat Biscuit", 5, "snacks"));
            groceryItemRepo.save(new items("Kodo Millet", "XYZ Kodo Millet healthy", 2, "millets"));
            groceryItemRepo.save(new items("Dried Red Chilli", "Dried Whole Red Chilli", 2, "spices"));
            groceryItemRepo.save(new items("Pearl Millet", "Healthy Pearl Millet", 1, "millets"));
            groceryItemRepo.save(new items("Cheese Crackers", "Bonny Cheese Crackers Plain", 6, "snacks"));*/
            System.out.println("Data creation complete...");
    }
}
