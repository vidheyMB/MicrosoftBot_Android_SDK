package com.msbot.directlineapis;


import com.msbot.directlineapis.model.response.BotActivity;

public interface BotListener {
    void onOpen();
    void onMessage(BotActivity botActivity);
    void onFailure();
}
