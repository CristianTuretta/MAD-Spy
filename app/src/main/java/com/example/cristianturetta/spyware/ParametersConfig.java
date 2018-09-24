package com.example.cristianturetta.spyware;

public class ParametersConfig {
    private static final int secondsBetweenExfiltration = 60;
    private static final int secondsBetweenScreenshot = 30;
    private static final boolean piggyBacked = true;

    public static int getSecondsBetweenExfiltration() {
        return secondsBetweenExfiltration;
    }

    public static int getSecondsBetweenScreenshot() {
        return secondsBetweenScreenshot;
    }

    public static boolean isPiggyBacked() {
        return piggyBacked;
    }
}
