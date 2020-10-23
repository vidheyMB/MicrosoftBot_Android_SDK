package com.msbot.directlineapis;


import com.msbot.directlineapis.model.response.BotActivity;

public interface BotListener {
    void onOpen();
    void onMessageObject(BotActivity botActivity);
    void onMessageString(String response);
    void onFailure();
}
