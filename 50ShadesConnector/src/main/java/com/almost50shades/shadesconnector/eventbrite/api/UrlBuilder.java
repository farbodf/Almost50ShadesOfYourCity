/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.almost50shades.shadesconnector.eventbrite.api;

/**
 *
 * @author martin
 */
public class UrlBuilder {

    public static final String EVENT_SEARCH = "https://www.eventbriteapi.com/v3/events/search/";
    private String token;

    UrlBuilder(String token) {
        this.token = token;
    }
    
    public String createUrl(String baseURL, Param ... params) {
        String url = baseURL + "?token=" + token;
        for (Param param : params) {
            url += "&" + param.key + "=" + param.value;
        }
        return url;
    }
    
    public String changeParam(String url, Param param) {
        int index = url.indexOf("&" + param.key + "=" );
        if (index == -1) {
            return url += "&" + param.key + "=" + param.value;
        } else {
            int end = url.indexOf("&", index);
            return url.replace(url.substring(index, end), param.key + "=" + param.value);
        }
    }
    
}
