package com.msbot.directlineapis.model.response;

import com.squareup.moshi.Json;

public class Image {

    @Json(name = "url")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}