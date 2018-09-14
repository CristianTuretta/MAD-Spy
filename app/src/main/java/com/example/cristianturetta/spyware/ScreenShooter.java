package com.example.cristianturetta.spyware;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ScreenShooter extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Bundle bundle = intent.getExtras();
        try {
            boolean hasActivity = bundle.containsKey("activity");
            if (hasActivity){
                Serializable serializable = bundle.getSerializable("activity");
                Activity activity = (AppCompatActivity) serializable;
                ScreenshotRunnable screenshotRunnable = new ScreenshotRunnable(activity);
                screenshotRunnable.run();
            }
        }catch (NullPointerException npe){
            Log.e("ScreenShooter", npe.getLocalizedMessage());
        }


    }

    public static void shoot(Activity activity){
        if (isExternalStorageWritable()) {
            View view = activity.getWindow().getDecorView();
            view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.setDrawingCacheEnabled(true);

            final Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            Date date = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

            try {
                FileOutputStream outputStream;

                outputStream = activity.openFileOutput(date + ".jpg", Context.MODE_PRIVATE);

                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

                outputStream.flush();
                outputStream.close();
                Log.d("Shoot:", "screenshot taken");
            } catch (Exception e) {
                Log.e("Shoot:", e.getLocalizedMessage());
            }
        }else {
            Log.d("Shoot:", "external storage is not writable");
        }
    }

    /**
     * Checks if external storage is available for read and write
     * */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public File getPublicAlbumStorageDir(String albumName) {
        // Get the directory for the user's public pictures directory.
        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("Error Directory:", "Directory not created");
        }
        return file;
    }

    private static class ScreenshotRunnable implements Runnable {
        private Handler handler = new Handler();
        private Activity activity;

        public ScreenshotRunnable(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void run() {
            shoot(activity);
            handler.postDelayed(this, 30000);
        }
    }
}
