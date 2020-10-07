package com.msbot.directlineapis.model;

import com.squareup.moshi.Json;

public class StartConversationModel {

    @Json(name = "conversationId")
    private String conversationId;
    @Json(name = "token")
    private String token;
    @Json(name = "expires_in")
    private Integer expiresIn;
    @Json(name = "streamUrl")
    private String streamUrl;
    @Json(name = "referenceGrammarId")
    private String referenceGrammarId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getStreamUrl() {
        return streamUrl;
    }

    public void setStreamUrl(String streamUrl) {
        this.streamUrl = streamUrl;
    }

    public String getReferenceGrammarId() {
        return referenceGrammarId;
    }

    public void setReferenceGrammarId(String referenceGrammarId) {
        this.referenceGrammarId = referenceGrammarId;
    }

}