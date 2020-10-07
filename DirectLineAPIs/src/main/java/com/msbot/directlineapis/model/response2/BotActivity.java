package com.msbot.directlineapis.model.response2;

import java.util.List;

import com.squareup.moshi.Json;

public class BotActivity {

    @Json(name = "activities")
    private List<Activity> activities = null;
    @Json(name = "watermark")
    private String watermark;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public String getWatermark() {
        return watermark;
    }

    public void setWatermark(String watermark) {
        this.watermark = watermark;
    }

}