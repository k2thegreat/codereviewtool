package com.codereviewtool.common.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RestTemplateUtil {

    public Optional<String> getJSONResultByURL(String url, HttpHeaders headers, HttpMethod httpMethod, RestTemplate template) throws URISyntaxException {
        URI uri = new URI(url);

        HttpEntity requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<String> result = template.exchange(uri, httpMethod, requestEntity, String.class);
        if (result.getStatusCodeValue() == 200) {
            System.out.println("Result :-" + result.getBody());
            return Optional.of(result.getBody());
        }
        return Optional.empty();
    }
}
