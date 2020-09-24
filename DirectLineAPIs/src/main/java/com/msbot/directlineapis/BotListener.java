package com.msbot.directlineapis;

import com.msbot.directlineapis.model.request.BotActivity;

public interface BotListener {
    void onResponse(BotActivity botActivity);
    void onFailure();
}
