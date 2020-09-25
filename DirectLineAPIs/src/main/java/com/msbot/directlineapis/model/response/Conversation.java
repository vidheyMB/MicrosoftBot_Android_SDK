package com.msbot.directlineapis.model.response;

import com.squareup.moshi.Json;

public class Conversation {

    @Json(name = "id")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}