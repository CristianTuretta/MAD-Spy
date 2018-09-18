package com.example.cristianturetta.spyware;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    private static FileUtil mInstance;

    private FileUtil() {}

    public static FileUtil getInstance() {
        if (mInstance == null) {
            synchronized (FileUtil.class) {
                if (mInstance == null) {
                    mInstance = new FileUtil();
                }
            }
        }
        return mInstance;
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
     * Gives an {@link ArrayList<File>} of JPG images.
     *
     * @param path  The {@link File} of the directory containing screenshots
     */
    public ArrayList<File> getAllImagesFrom(File path){
        File[] allFiles = path.listFiles();
        ArrayList<File> allImages = new ArrayList<>();
        for (File file: allFiles) {
            if (file.getName().endsWith(".jpg")){
                allImages.add(file);
            }
        }
        return allImages;
    }
}