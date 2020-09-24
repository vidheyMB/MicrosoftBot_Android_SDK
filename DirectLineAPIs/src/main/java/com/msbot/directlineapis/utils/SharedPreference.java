package com.msbot.directlineapis.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.response.StartConversationModel;

import java.io.IOException;

public class SharedPreference {

    private static final String SharedPreferenceName = "AzureBotFramework";
    private static final String ConversationID = "ConversationID";

    public static void setData(Context context, StartConversationModel startConversationModel){
        SharedPreferences.Editor editor = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE).edit();
        editor.putString(ConversationID, DirectLineAPI.getInstance().moshi
                .adapter(StartConversationModel.class).toJson(startConversationModel));
        editor.apply();
    }

    public static StartConversationModel getData(Context context) throws IOException {
        SharedPreferences prefs = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE);
        return DirectLineAPI.getInstance().moshi.adapter(StartConversationModel.class)
                .fromJson(prefs.getString(ConversationID, ""));
    }

}
