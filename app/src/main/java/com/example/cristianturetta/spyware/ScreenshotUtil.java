package com.example.cristianturetta.spyware;

import android.graphics.Bitmap;
import android.view.View.MeasureSpec;
import android.view.View;

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

}
