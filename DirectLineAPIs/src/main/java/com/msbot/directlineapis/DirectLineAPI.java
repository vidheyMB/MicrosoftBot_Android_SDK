package com.msbot.directlineapis;

import android.content.Context;
import android.util.Log;

import com.msbot.directlineapis.APIClient.SocketListener;
import com.msbot.directlineapis.model.request.BotActivity;
import com.msbot.directlineapis.model.request.From;
import com.msbot.directlineapis.model.response.StartConversationModel;
import com.msbot.directlineapis.utils.SharedPreference;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;

public class DirectLineAPI {

    private static final String TAG = "DirectLineAPI";

    private static DirectLineAPI directLineAPI;

    private static final String BOT_BASE_URL = "https://directline.botframework.com/v3/directline/conversations/";

    private String SECRET_KEY;
    private Context context;

    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder okHttpClientBuilder;
    private WebSocket ws;
    private Request request;
    public Moshi moshi;
    private Call call;

    private StartConversationModel startConversationModel;

    private BotListener botListener;

    public static DirectLineAPI getInstance() {
        if (directLineAPI == null)
            directLineAPI = new DirectLineAPI();
        return directLineAPI;
    }

    public void start(Context context, String SECRET_KEY, BotListener botListener) {

        if (okHttpClient != null) return;

        this.botListener = botListener;
        this.SECRET_KEY = SECRET_KEY;
        this.context = context;

        moshi = new Moshi.Builder().build();

        okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        okHttpClient = okHttpClientBuilder.build();


        startConversation();
    }


    public void startConversation() {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, "{}");

        request = new Request.Builder()
                .url(BOT_BASE_URL)
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                .addHeader("Content-Type", "application/json") //Notice this request has header, if you don't need remove this part.
                .build();

        call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "================= Start Conversation Failure  ==================");
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 201) {
                    startConversationModel = moshi.adapter(StartConversationModel.class)
                            .fromJson(Objects.requireNonNull(response.body()).string());

                    if (startConversationModel != null) {
                        SharedPreference.setData(context, startConversationModel);
                        receiveActivities(startConversationModel.getStreamUrl());
                    }
                }
            }
        });

    }


    public void reconnectConversation() {

        request = new Request.Builder()
                .url(BOT_BASE_URL + startConversationModel.getConversationId())
                .get()
                .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                .build();

        call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "================= Reconnect Conversation Failure  ==================");
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                    startConversationModel = moshi.adapter(StartConversationModel.class)
                            .fromJson(Objects.requireNonNull(response.body()).string());

                    assert startConversationModel != null;
                    receiveActivities(startConversationModel.getStreamUrl());
                }
            }
        });

    }


    private void receiveActivities(String webSocketURL) {

        request = new Request.Builder().url(webSocketURL).build();
        SocketListener listener = new SocketListener(botListener);
        ws = okHttpClient.newWebSocket(request, listener);
//        okHttpClient.dispatcher().executorService().shutdown();

    }


    public void sendActivity(String MSG) {

        if (startConversationModel == null) return;

        From from = new From();
        from.setId("User1");

        BotActivity botActivity = new BotActivity();
        botActivity.setLocale("en-EN");
        botActivity.setType("message");
        botActivity.setFrom(from);
        botActivity.setText(MSG);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, moshi.adapter(BotActivity.class).toJson(botActivity));
        Log.i(TAG, moshi.adapter(BotActivity.class).toJson(botActivity));
        Log.i(TAG, BOT_BASE_URL + startConversationModel.getConversationId() + "/activities");

        Request request = new Request.Builder()
                .url(BOT_BASE_URL + startConversationModel.getConversationId() + "/activities")
                .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                .addHeader("Content-Type", "application/json") //Notice this request has header, if you don't need remove this part.
                .post(body)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "================= Send Activity Failure  ==================");
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.code() == 200) {
                }
            }
        });

    }


}