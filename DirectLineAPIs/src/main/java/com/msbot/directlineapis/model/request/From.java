package com.msbot.directlineapis.model.request;

import com.squareup.moshi.Json;

public class From {

    @Json(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}