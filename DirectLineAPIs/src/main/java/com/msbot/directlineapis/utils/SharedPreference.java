package com.msbot.directlineapis.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.response.StartConversationModel;

import java.io.IOException;
import java.util.Objects;

public class SharedPreference {

    private static final String SharedPreferenceName = "AzureBotFramework";
    private static final String ConversationID = "ConversationID";
    private static final String WaterMark = "WaterMark";

    private static SharedPreference sharedPreference = null;

    private static SharedPreferences.Editor editor;
    private static SharedPreferences prefs;
    private Context context;

    public static synchronized SharedPreference getInstance() {
        if (sharedPreference == null) sharedPreference = new SharedPreference();
        return sharedPreference;
    }

    public void setApplicationContext(Context contexts) {
        context = contexts;
    }

    public void setConversationData(StartConversationModel startConversationModel) {
        editor = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE).edit();
        editor.putString(ConversationID, DirectLineAPI.getInstance().moshi
                .adapter(StartConversationModel.class).toJson(startConversationModel));
        editor.apply();
    }

    public StartConversationModel getConversationData() {
        prefs = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE);
        if (Objects.requireNonNull(prefs.getString(ConversationID, "")).isEmpty()) {
            return null;
        } else {
            try {
                return DirectLineAPI.getInstance().moshi.adapter(StartConversationModel.class)
                        .fromJson(Objects.requireNonNull(prefs.getString(ConversationID, "")));
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setWaterMarkData(String waterMark) {
        editor = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE).edit();
        editor.putString(WaterMark, waterMark);
        editor.apply();
    }

    public String getWaterMarkData() {
        prefs = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE);
        return prefs.getString(WaterMark, "");
    }

    public void clearSharedPreference(){
        editor.clear();
        editor = null;
        prefs = null;
    }

    public static void sharedPreferenceDestroy(){
        sharedPreference = null;
        editor = null;
        prefs = null;

        System.gc();
    }

}
