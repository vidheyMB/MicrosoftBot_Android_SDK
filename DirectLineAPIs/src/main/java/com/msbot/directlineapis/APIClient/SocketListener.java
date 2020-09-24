package com.msbot.directlineapis.APIClient;

import android.util.Log;

import com.msbot.directlineapis.BotListener;
import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.request.BotActivity;

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
            botListener.onResponse(DirectLineAPI.getInstance().moshi.adapter(BotActivity.class).fromJson(text));
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
        Log.e(TAG, "$$$$$$$$$$$$$$$$$$$$$ Failure Socket $$$$$$$$$$$$$$$$$$$$$");
        Log.e(TAG, t.getMessage() + " - " + response);
        botListener.onFailure();
    }
}

