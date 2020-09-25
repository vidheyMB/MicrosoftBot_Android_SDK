package com.msbot.directlineapis.model.request;

import com.squareup.moshi.Json;

public class ActivityRequest {

    @Json(name = "locale")
    private String locale;
    @Json(name = "type")
    private String type;
    @Json(name = "from")
    private From from;
    @Json(name = "text")
    private String text;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}