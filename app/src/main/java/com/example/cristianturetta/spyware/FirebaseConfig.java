package com.example.cristianturetta.spyware;

import java.util.List;

public class FirebaseConfig {

    private static final String apiKey = "AIzaSyBFwltiQ43GREUlgPZRJNG3qo6cx3HZNh0";
    private static final String authDomain = "mads-50eb5.firebaseapp.com";
    private static final String databaseURL = "https://mads-50eb5.firebaseio.com";
    private static final String projectId = "mads-50eb5";
    private static final String storageBucket = "mads-50eb5.appspot.com";
    private static final String messagingSenderId = "834670029342";

    public static String getApiKey() {
        return apiKey;
    }

    public static String getAuthDomain() {
        return authDomain;
    }

    public static String getDatabaseURL() {
        return databaseURL;
    }

    public static String getProjectId() {
        return projectId;
    }

    public static String getStorageBucket() {
        return storageBucket;
    }

    public static String getMessagingSenderId() {
        return messagingSenderId;
    }
}
