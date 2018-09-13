package com.example.cristianturetta.spyware;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class Spy extends AccessibilityService {

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d("Spy", "Start Attack");
        ScreenShooter screenShooter;

    }

    @Override
    public void onInterrupt() {

    }
}
