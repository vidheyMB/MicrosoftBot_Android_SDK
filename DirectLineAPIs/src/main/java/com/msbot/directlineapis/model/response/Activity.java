package com.msbot.directlineapis.model.response;

import java.util.List;

import com.squareup.moshi.Json;

public class Activity {

    @Json(name = "type")
    private String type;
    @Json(name = "id")
    private String id;
    @Json(name = "timestamp")
    private String timestamp;
    @Json(name = "channelId")
    private String channelId;
    @Json(name = "from")
    private From from;
    @Json(name = "conversation")
    private Conversation conversation;
    @Json(name = "attachmentLayout")
    private String attachmentLayout;
    @Json(name = "locale")
    private String locale;
    @Json(name = "text")
    private String text;
    @Json(name = "speak")
    private String speak;
    @Json(name = "inputHint")
    private String inputHint;
    @Json(name = "suggestedActions")
    private SuggestedActions suggestedActions;
    @Json(name = "attachments")
    private List<Attachment> attachments = null;
    @Json(name = "entities")
    private List<Object> entities = null;
    @Json(name = "replyToId")
    private String replyToId;

    public SuggestedActions getSuggestedActions() {
        return suggestedActions;
    }

    public void setSuggestedActions(SuggestedActions suggestedActions) {
        this.suggestedActions = suggestedActions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getAttachmentLayout() {
        return attachmentLayout;
    }

    public void setAttachmentLayout(String attachmentLayout) {
        this.attachmentLayout = attachmentLayout;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSpeak() {
        return speak;
    }

    public void setSpeak(String speak) {
        this.speak = speak;
    }

    public String getInputHint() {
        return inputHint;
    }

    public void setInputHint(String inputHint) {
        this.inputHint = inputHint;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void setEntities(List<Object> entities) {
        this.entities = entities;
    }

    public String getReplyToId() {
        return replyToId;
    }

    public void setReplyToId(String replyToId) {
        this.replyToId = replyToId;
    }

}