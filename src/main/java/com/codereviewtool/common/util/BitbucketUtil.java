package com.codereviewtool.common.util;

import com.codereviewtool.common.model.bitbucket.pullrequests.Root;

public class BitbucketUtil {

    public static final String UI_FORM = "UIFORM";
    public static final String UI_CONTROL =	"UICONTROL";
    public static final String HOME = "HOME";
    public static final String XML = "XML";
    public static final String END_POINT = "ENDPOINT";
    public static final String SERVICE = "SERVICE";
    public static final String UTIL	= "UTIL";
    public static final String JS = "JS";
    public static final String JSON = "JSON";
    public static final String CSS = "CSS";
    public static final String HTML = "HTML";
    public static final String OTHERS = "OTHERS";

    public static String setQueryParam(String url, String param, String value, boolean isFirstParam) {
        return url + (isFirstParam ? "?" : "&&") + param + "=" + value;
    }

    public Root getPullRequestsByUrl(){
        return new Root();
    }

    public static String getFileType(String fileName) {

        String fileNameUpperCase = fileName.toUpperCase();
        if (fileNameUpperCase.contains(UI_FORM)) {
            return "UI_FORM";
        } else if (fileNameUpperCase.contains(UI_CONTROL)) {
            return "UI_CONTROL";
        } else if (fileNameUpperCase.contains(HOME)) {
            return "HOME";
        } else if (fileNameUpperCase.contains(XML)) {
            return "XML";
        } else if (fileNameUpperCase.contains(END_POINT)) {
            return "END_POINT";
        } else if (fileNameUpperCase.contains(SERVICE)) {
            return "SERVICE";
        } else if (fileNameUpperCase.contains(UTIL)) {
            return "UTIL";
        } else if (fileNameUpperCase.contains(JS)) {
            return "JS";
        } else if (fileNameUpperCase.contains(JSON)) {
            return "JSON";
        } else if (fileNameUpperCase.contains(CSS)) {
            return "CSS";
        } else if (fileNameUpperCase.contains(HTML)) {
            return "HTML";
        }
        return "OTHERS";

    }

}
