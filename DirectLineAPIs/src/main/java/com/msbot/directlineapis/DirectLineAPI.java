package com.msbot.directlineapis;

import android.content.Context;
import android.util.Log;

import com.msbot.directlineapis.model.request.ActivityRequest;
import com.msbot.directlineapis.model.request.From;
import com.msbot.directlineapis.model.StartConversationModel;
import com.msbot.directlineapis.utils.SharedPreference;
import com.squareup.moshi.Moshi;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
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

    private static DirectLineAPI directLineAPI = null;

    private static final String BOT_BASE_URL = "https://directline.botframework.com/v3/directline/conversations/";

    private String SECRET_KEY;

    private OkHttpClient okHttpClient;
    private WebSocket ws;
    private Request request;
    public Moshi moshi;
    private Call call;

    private Boolean withWatermark = false;
    private Boolean KeepConversationSame = false;
    private String FromUser;

    private StartConversationModel startConversationModel;

    private BotListener botListener;

    public static synchronized DirectLineAPI getInstance() {
        if (directLineAPI == null)
            directLineAPI = new DirectLineAPI();
        return directLineAPI;
    }

    public void start(Context context, String FromUser, String SECRET_KEY, Boolean KeepConversationSame, Boolean withWatermark, BotListener botListener) {

        if (okHttpClient != null) return;

        this.botListener = botListener;
        this.SECRET_KEY = SECRET_KEY;
        this.KeepConversationSame = KeepConversationSame;
        this.withWatermark = withWatermark;
        this.FromUser = (FromUser == null ? "User1" : FromUser);

        moshi = new Moshi.Builder().build();
        SharedPreference.getInstance().setApplicationContext(context);
        SocketListener.getInstance().setMainHandler(context);

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS);

        okHttpClient = okHttpClientBuilder.build();

        startConversation(this.KeepConversationSame);

    }


    private void startConversation(Boolean KeepConversationSame) {
        /*
         *   Check for conversation id exists, if its exist use the same or Create new .
         * */
        if (SharedPreference.getInstance().getConversationData() != null && KeepConversationSame) {
            /*
             *   Start the webSockets to receive the messages from bot.
             * */
            startConversationModel = SharedPreference.getInstance().getConversationData();
            assert startConversationModel != null;
            receiveActivities(startConversationModel.getStreamUrl());

        } else {

            /*
             *   Call service to get the Conversation ID, Token and WebSocket URL.
             * */

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, "{}");

            request = new Request.Builder()
                    .url(BOT_BASE_URL)
                    .cacheControl(new CacheControl.Builder().noCache().build())
                    .post(body)
                    .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                    .addHeader("Content-Type", "application/json") //Notice this request has header, if you don't need remove this part.
                    .build();

            call = okHttpClient.newCall(request);

            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    Log.e(TAG, "================= Start Conversation Failure  ==================");
                    Log.e(TAG, Objects.requireNonNull(e.getMessage()));

                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    /*
                     *   Response Code 201 => Create new Conversation ID.
                     * */
                    if (response.code() == 201) {
                        startConversationModel = moshi.adapter(StartConversationModel.class)
                                .fromJson(Objects.requireNonNull(response.body()).string());

                        if (startConversationModel != null) {
                            /*
                             *  Save the Start Conversation ID in sharedPreference.
                             * */
                            SharedPreference.getInstance().setConversationData(startConversationModel);
                            receiveActivities(startConversationModel.getStreamUrl());
                        }
                    } else if (response.code() == 403) {
                        Log.e(TAG, "================= Start Conversation Failure  ==================");
                        Log.e(TAG, "description : You are forbidden from performing this action because your token or secret is invalid.");
                    }
                }
            });

        }

    }

    /*
     * End of Conversation
     * */
    private void endConversation() {

        if (startConversationModel == null) return;

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON,
                "{\n" +
                        "    \"type\": \"endOfConversation\",\n" +
                        "    \"from\": {\n" +
                        "        \"id\": " + FromUser + "\n" +
                        "    }\n" +
                        "}");

        request = new Request.Builder()
                .url(BOT_BASE_URL + startConversationModel.getConversationId() + "/activities")
                .cacheControl(new CacheControl.Builder().noCache().build())
                .post(body)
                .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                .addHeader("Content-Type", "application/json") //Notice this request has header, if you don't need remove this part.
                .build();

        call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.e(TAG, "================= End of Conversation Failure  ==================");
                Log.e(TAG, Objects.requireNonNull(e.getMessage()));

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                /*
                 *   Response Code 200 => End of Conversation.
                 * */
                if (response.code() == 200) {
                    Log.e(TAG, "================= End of Conversation  ==================");
                } else {
                    Log.e(TAG, "================= End of Conversation Failure  ==================");
                }

                onDestroy();
            }
        });

    }

    /*
     *   Reconnect the conversation with or without WaterMark -> user optional.
     * */
   /* public void reconnectWithWatermark(Boolean withWatermark) {
        this.withWatermark = withWatermark;
    }*/

    /*
     *   Reconnect the conversation if session expired.
     * */
    public void reconnectConversation() {

        String URL = BOT_BASE_URL + startConversationModel.getConversationId();

        /*
         *   Add WaterMark to ReconnectConversation, its Optional.
         * */
        if (!SharedPreference.getInstance().getWaterMarkData().isEmpty() && withWatermark)
            URL += "?watermark=" + SharedPreference.getInstance().getWaterMarkData();

        request = new Request.Builder()
                .url(URL)
                .cacheControl(new CacheControl.Builder().noCache().build())
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

                    if (startConversationModel != null) {
                        /*
                         *  Update the Start Conversation ID in sharedPreference.
                         * */
                        SharedPreference.getInstance().setConversationData(startConversationModel);
                        receiveActivities(startConversationModel.getStreamUrl());
                    }
                } else if (response.code() == 403) {
                    Log.e(TAG, "================= Reconnect Conversation Failure  ==================");
                    Log.e(TAG, "description : You are forbidden from performing this action because your token or secret is invalid.");
                }
            }
        });

    }

    /*
     *    WebSocket Listener start receive the bot messages.
     * */
    private void receiveActivities(String webSocketURL) {

        request = new Request.Builder()
                .url(webSocketURL)
                .cacheControl(new CacheControl.Builder().noCache().build())
                .build();
        SocketListener listener = SocketListener.getInstance();
        listener.setBotListener(botListener);
        ws = okHttpClient.newWebSocket(request, listener);
//        okHttpClient.dispatcher().executorService().shutdown();

    }

    /*
     *   Send messages to bot.
     * */
    public void sendActivity(String MSG) {

        if (startConversationModel == null) return;

        From from = new From();
        from.setId(FromUser);

        ActivityRequest botActivity = new ActivityRequest();
        botActivity.setLocale("en-US");
        botActivity.setType("message");
        botActivity.setFrom(from);
        botActivity.setText(MSG);

        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, moshi.adapter(ActivityRequest.class).toJson(botActivity));

        Request request = new Request.Builder()
                .url(BOT_BASE_URL + startConversationModel.getConversationId() + "/activities")
                .addHeader("Authorization", "Bearer " + SECRET_KEY) //Notice this request has header, if you don't need remove this part.
                .addHeader("Content-Type", "application/json") //Notice this request has header, if you don't need remove this part.
                .post(body)
                .cacheControl(new CacheControl.Builder().noCache().build())
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
                if (response != null && response.code() == 200) {
                } else if (response != null && response.code() == 403) {
                    Log.e(TAG, "================= Send Activity Failure  ==================");
                    Log.e(TAG, "description : You are forbidden from performing this action because your token or secret is invalid.");
                } else {
                    Log.e(TAG, "================= Send Activity Failure  ==================");
                    Log.e(TAG, "Status Code :" + response.code());
                    Log.e(TAG, "description : Something went wrong.");

                }
            }
        });

    }

    /*
     *   getConversation data exists or not.
     * */
    public boolean isConversationExists() {
        if (this.KeepConversationSame) {
            return (SharedPreference.getInstance().getConversationData() != null ? true : false);
        } else {
            return false;
        }
    }

    /*
     *   Destroy the instances after used.
     * */
    public void destroy() {

        if (!KeepConversationSame) {
            endConversation();
        } else {
            onDestroy();
        }


    }

    private void onDestroy() {
        ws.cancel();
        okHttpClient.dispatcher().executorService().shutdown();

        directLineAPI = null;
        SECRET_KEY = null;
        okHttpClient = null;
        ws = null;
        request = null;
        moshi = null;
        call = null;
        startConversationModel = null;
        botListener = null;

        SharedPreference.sharedPreferenceDestroy();

        System.gc();
    }

}