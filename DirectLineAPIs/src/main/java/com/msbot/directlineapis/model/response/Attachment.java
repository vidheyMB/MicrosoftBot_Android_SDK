package com.msbot.directlineapis.model.response;

import com.squareup.moshi.Json;

public class Attachment {

    @Json(name = "contentType")
    private String contentType;
    @Json(name = "content")
    private Content content;

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

}