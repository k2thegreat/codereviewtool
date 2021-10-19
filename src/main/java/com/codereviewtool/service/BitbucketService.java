package com.codereviewtool.service;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.common.model.bitbucket.pullrequests.Root;
import com.codereviewtool.common.model.bitbucket.pullrequests.diff.Diff;
import com.codereviewtool.common.model.bitbucket.pullrequests.diff.Source;
import com.codereviewtool.common.util.BitbucketUtil;
import com.codereviewtool.common.util.RestTemplateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BitbucketService {

  @Value("${codereviewtool.bitbucket.pullrequestsurl}")
  private String pullRequestsURL;

  @Value("${codereviewtool.bitbucket.pullrequestactivitiesurl}")
  private String pullRequestActivitiesURL;

  @Value("${codereviewtool.bitbucket.authorization}")
  private String bitbucketAuthorization;

  @Value("${codereviewtool.bitbucket.pullrequestdiffurl}")
  private String pullRequestsDiffURL;

  @Autowired private RestTemplateUtil restTemplateUtil;

  @Autowired private RestTemplate restTemplate;

  @Autowired private ObjectMapper objectMapper;

  private final int YEAR_TILL_2019 = 2020;
  private final int BITBUCKET_API_FETCH_SIZE = 100;
  private final String COMMITS = "commits";
  private final String DIFF = "diff";
  private final String OVERVIEW = "overview";

  public Optional<List<Root>> getPullRequests(String limit, String start, String slug) {
    boolean isLastPage = false;
    int date = getYear(String.valueOf(System.currentTimeMillis()));
    Root root = null;
    List<Root> rootList = new ArrayList();
    while (!isLastPage && date > YEAR_TILL_2019) {
      String url = BitbucketUtil.setQueryParam(pullRequestsURL, "limit", limit, false);
      url = BitbucketUtil.setQueryParam(url, "start", start, false);
      url = BitbucketUtil.setQueryParam(url, "slug", slug, false);

      HttpHeaders headers = new HttpHeaders();
      headers.set("Authorization", bitbucketAuthorization);
      try {
        Optional<String> result =
            restTemplateUtil.getJSONResultByURL(url, headers, HttpMethod.GET, restTemplate);
        if (result.isPresent()) {
          root = objectMapper.readValue(result.get(), Root.class);
          start = String.valueOf((Integer.valueOf(start) + BITBUCKET_API_FETCH_SIZE));
          if (root != null) {
            isLastPage = root.isLastPage();
            List<com.codereviewtool.common.model.bitbucket.pullrequests.Value> values =
                root.getValues();
            if (values != null && values.size() > 0) {
              date = getYear(values.get(0).getCreatedDate());
            }
          }

          rootList.add(root);
        }
      } catch (URISyntaxException ex) {

      } catch (JsonMappingException e) {
        e.printStackTrace();
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
    }

    return Optional.of(rootList);
  }

  public Optional<List<com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root>>
      getPullRequestsActivities(String limit, String start, String slug) {
    List<com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root> rootList =
        new ArrayList();

    List<Root> roots = getPullRequests(limit, start, slug).get();

    if (roots.isEmpty()) return Optional.empty();

    for (Root data : roots) {
      for (com.codereviewtool.common.model.bitbucket.pullrequests.Value value : data.getValues()) {
        int pullRequestId = value.getId();
        String url = pullRequestActivitiesURL.replace("REQ_ID", String.valueOf(pullRequestId));
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", bitbucketAuthorization);
        try {
          Optional<String> result =
              restTemplateUtil.getJSONResultByURL(url, headers, HttpMethod.GET, restTemplate);
          if (result.isPresent()) {
            com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root root =
                objectMapper.readValue(
                    result.get(),
                    com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root.class);
            if (root != null) {
              if (isValid(root)) {
                rootList.add(root);
              }
            }
          }

        } catch (URISyntaxException ex) {

        } catch (JsonMappingException e) {
          e.printStackTrace();
        } catch (JsonProcessingException e) {
          e.printStackTrace();
        }
      }
    }

    return Optional.of(rootList);
  }

  private boolean isValid(
      com.codereviewtool.common.model.bitbucket.pullrequests.activities.Root root) {
    return root.getValues().stream()
        .anyMatch(value -> value.action.equals("COMMENTED") && value.commentAction.equals("ADDED"));
  }

  public Optional<Set<String>> getFileTypes(String pullRequestsURL) {
    String pullRequestId = getPullRequestIdFromURL(pullRequestsURL);
    String pullReqActivitiesURL = pullRequestsDiffURL.replace("REQ_ID", pullRequestId);
    HttpHeaders headers = new HttpHeaders();
    com.codereviewtool.common.model.bitbucket.pullrequests.diff.Root root = null;
    headers.set("Authorization", bitbucketAuthorization);
    Set<String> fileTypes = new HashSet();
    try {
      Optional<String> result =
          restTemplateUtil.getJSONResultByURL(
              pullReqActivitiesURL, headers, HttpMethod.GET, restTemplate);
      if (result.isPresent()) {
        root =
            objectMapper.readValue(
                result.get(),
                com.codereviewtool.common.model.bitbucket.pullrequests.diff.Root.class);
        if (root != null) {
          List<com.codereviewtool.common.model.bitbucket.pullrequests.diff.Diff> diffs =
              root.getDiffs();
          if (diffs != null && diffs.size() > 0) {
            for (Diff diff : diffs) {
              Source src = diff.getSource();
              if (src != null) {
                fileTypes.add(BitbucketUtil.getFileType(src.getName()));
              }
            }
          }
        }
      }
    } catch (URISyntaxException ex) {

    } catch (JsonMappingException e) {
      e.printStackTrace();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return Optional.of(fileTypes);
  }

  private int getYear(String date) {
    DateFormat formatter = new SimpleDateFormat("yyyy");

    long milliSeconds = Long.parseLong(date);
    System.out.println(milliSeconds);

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(milliSeconds);
    return Integer.valueOf(formatter.format(calendar.getTime()));
  }

  public static void main(String[] args) {
    String date =
        "https://wfrbitbucket.int.kronos.com/rest/api/1.0/projects/wfr/repos/zeyt/pull-requests/59896/activities";
    date =
        date.substring(
            date.indexOf("pull-requests/") + "pull-requests/".length(),
            date.indexOf("/activities"));
    System.out.println("PR Id :-" + date);
  }

  private String getPullRequestIdFromURL(String url) {
    String endingString =
        url.endsWith(COMMITS)
            ? COMMITS
            : (url.endsWith(DIFF) ? DIFF : (url.endsWith(OVERVIEW) ? OVERVIEW : ""));
    return url.substring(
        url.indexOf("pull-requests/") + "pull-requests/".length(), url.indexOf("/" + endingString));
  }
}
