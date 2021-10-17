package com.codereviewtool.service;

import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.common.model.bitbucket.pullrequests.Root;
import com.codereviewtool.common.util.BitbucketUtil;
import com.codereviewtool.common.util.RestTemplateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BitbucketService {

    @Value("${codereviewtool.bitbucket.pullrequestsurl}")
    private String pullRequestsURL;

    @Value("${codereviewtool.bitbucket.authorization}")
    private String bitbucketAuthorization;

    @Autowired
    private RestTemplateUtil restTemplateUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final int YEAR_TILL_2019 = 2020;
    private final int BITBUCKET_API_FETCH_SIZE = 100;

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
                Optional<String> result = restTemplateUtil.getJSONResultByURL(url, headers, HttpMethod.GET, restTemplate);
                if (result.isPresent()) {
                    root = objectMapper.readValue(result.get(), Root.class);
                    start = String.valueOf((Integer.valueOf(start) + BITBUCKET_API_FETCH_SIZE));
                    if (root != null) {
                        isLastPage = root.isLastPage();
                        List<com.codereviewtool.common.model.bitbucket.pullrequests.Value> values = root.getValues();
                        if(values != null && values.size() > 0){
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

    private int getYear(String date){
        DateFormat formatter = new SimpleDateFormat("yyyy");

        long milliSeconds= Long.parseLong(date);
        System.out.println(milliSeconds);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return Integer.valueOf(formatter.format(calendar.getTime()));
    }

    public static void main(String[] args) {
        String date = "1568102544200";

    }


}