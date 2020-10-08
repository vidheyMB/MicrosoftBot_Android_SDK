package com.msbot.directlineapis.model.response;

import com.squareup.moshi.Json;

public class Button {

    @Json(name = "type")
    private String type;
    @Json(name = "title")
    private String title;
    @Json(name = "image")
    private String image;
    @Json(name = "text")
    private String text;
    @Json(name = "displayText")
    private String displayText;
    @Json(name = "value")
    private String value;
    @Json(name = "channelData")
    private String channelData;
    @Json(name = "imageAltText")
    private String imageAltText;

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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChannelData() {
        return channelData;
    }

    public void setChannelData(String channelData) {
        this.channelData = channelData;
    }

    public String getImageAltText() {
        return imageAltText;
    }

    public void setImageAltText(String imageAltText) {
        this.imageAltText = imageAltText;
    }
}