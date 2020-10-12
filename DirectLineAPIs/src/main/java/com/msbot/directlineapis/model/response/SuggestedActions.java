package com.msbot.directlineapis.model.response;

import com.squareup.moshi.Json;

import java.util.List;

public class SuggestedActions {

    @Json(name = "actions")
    private List<Action> actions = null;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

}