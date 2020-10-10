package com.msbot.microsoftbotandroidsdk;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.msbot.directlineapis.BotListener;
import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.response.BotActivity;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textClick);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectLineAPI.getInstance().sendActivity("hi");
            }
        });

        DirectLineAPI.getInstance().start(getApplication(),"asdfghjk", new BotListener() {
            @Override
            public void onOpen() {
                DirectLineAPI.getInstance().sendActivity("Hi");
            }

            @Override
            public void onMessage(final BotActivity botActivity) {
                textView.setText(botActivity.getActivities().get(0).getText()+"\n");
            }

            @Override
            public void onFailure() {

            }
        });


    }
}