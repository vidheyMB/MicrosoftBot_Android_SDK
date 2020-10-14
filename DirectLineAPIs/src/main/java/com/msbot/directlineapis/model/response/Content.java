package com.msbot.directlineapis.model.response;

import java.util.List;

import com.squareup.moshi.Json;

public class Content {

    @Json(name = "title")
    private String title;
    @Json(name = "subtitle")
    private String subtitle;
    @Json(name = "text")
    private String text;
    @Json(name = "images")
    private List<Image> images = null;
    @Json(name = "buttons")
    private List<Button> buttons = null;
    @Json(name = "lgtype")
    private String lgtype;
    @Json(name = "value")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }

    public String getLgtype() {
        return lgtype;
    }

    public void setLgtype(String lgtype) {
        this.lgtype = lgtype;
    }

}