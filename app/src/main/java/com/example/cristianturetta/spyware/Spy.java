package com.example.cristianturetta.spyware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Spy extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("StartAttack", "onReceive");
    }
}
