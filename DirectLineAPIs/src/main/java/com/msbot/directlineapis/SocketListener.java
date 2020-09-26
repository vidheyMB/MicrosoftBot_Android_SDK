package com.msbot.directlineapis;

import android.util.Log;

import com.msbot.directlineapis.BotListener;
import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.response.BotActivity;
import com.msbot.directlineapis.utils.SharedPreference;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.io.IOException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class SocketListener extends WebSocketListener {

    private static final String TAG = "SocketListener";
    private static final int NORMAL_CLOSURE_STATUS = 1000;
    BotListener botListener;

    public SocketListener(BotListener botListener) {
        this.botListener = botListener;
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        Log.d(TAG, "$$$$$$$$$$$$$$$$$$$$$ Open Socket $$$$$$$$$$$$$$$$$$$$$$$$");
//        webSocket.send("Hello, it's SSaurel !");
//        webSocket.send("What's up ?");
//        webSocket.send(ByteString.decodeHex("deadbeef"));
//        webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        try {
            Log.d(TAG, "$$$$$$$$$$$$$$$$$$$$$ Message From Socket $$$$$$$$$$$$$$$$$$$$$");
            Log.d(TAG, text);

            if (!text.isEmpty()) {
                /*
                *   Send Response to user
                * */
                BotActivity botActivity = DirectLineAPI.getInstance().moshi.adapter(BotActivity.class).fromJson(text);
                botListener.onResponse(botActivity);

                /*
                *   Set WaterMark in SharedPreference
                * */
                assert botActivity != null;
                if(botActivity.getWatermark()!=null)
                SharedPreference.getInstance().setWaterMarkData(botActivity.getWatermark());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        Log.d(TAG, "$$$$$$$$$$$$$$$$$$$$$ Closing Socket $$$$$$$$$$$$$$$$$$$$$");
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, @NotNull Throwable t, @Nullable Response response) {

        if(response!=null && response.code() == 403){
            DirectLineAPI.getInstance().reconnectConversation();
        }else{
            Log.e(TAG, "$$$$$$$$$$$$$$$$$$$$$ Failure Socket $$$$$$$$$$$$$$$$$$$$$");
            Log.e(TAG, t.getMessage() + " - " + response);
            botListener.onFailure();
        }
    }
}

