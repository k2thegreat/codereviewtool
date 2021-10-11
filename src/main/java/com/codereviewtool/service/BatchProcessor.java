package com.codereviewtool.service;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.codereviewtool.common.util.StringUtil;

@Service
public class BatchProcessor {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${codereviewtool.bitbucket.allrepos}")
    private String bitbucketAllReposPath;

    @Value("${codereviewtool.bitbucket.authorization}")
    private String bitbucketAuthorizaation;

    public void processDetails() throws URISyntaxException {

        if (!StringUtil.isEmpty(bitbucketAllReposPath) && !StringUtil.isEmpty(bitbucketAuthorizaation)) {
            URI uri = new URI(bitbucketAllReposPath);
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", bitbucketAuthorizaation);

            HttpEntity requestEntity = new HttpEntity<>(null, headers);

            ResponseEntity<String> result = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, String.class);
            if (result.getStatusCodeValue() == 200) {
                System.out.println("Result :-" + result.getBody());
            }
        }

    }
}
