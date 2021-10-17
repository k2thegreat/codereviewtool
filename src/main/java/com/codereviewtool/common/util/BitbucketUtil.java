package com.codereviewtool.common.util;

import com.codereviewtool.common.model.bitbucket.pullrequests.Root;

public class BitbucketUtil {

    public static String setQueryParam(String url, String param, String value, boolean isFirstParam) {
        return url + (isFirstParam ? "?" : "&&") + param + "=" + value;
    }

    public Root getPullRequestsByUrl(){
        return new Root();
    }

}
