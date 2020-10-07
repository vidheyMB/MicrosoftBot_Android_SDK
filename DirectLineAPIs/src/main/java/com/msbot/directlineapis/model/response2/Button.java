package com.msbot.directlineapis.model.response2;

import com.squareup.moshi.Json;

public class Button {

    @Json(name = "type")
    private String type;
    @Json(name = "title")
    private String title;
    @Json(name = "value")
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}