package com.msbot.microsoftbotandroidsdk;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.msbot.directlineapis.BotListener;
import com.msbot.directlineapis.DirectLineAPI;
import com.msbot.directlineapis.model.request.BotActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.textClick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DirectLineAPI.getInstance().sendActivity("hi");
            }
        });

        DirectLineAPI.getInstance().start(this,"r2N1NXc_Xtw.Y_Hu4zdjpNURVsSEDIS2IHcugNzM2VWK1J-QvwnyKuo", new BotListener() {
            @Override
            public void onResponse(BotActivity botActivity) {

            }

            @Override
            public void onFailure() {

            }
        });


    }
}