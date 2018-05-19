package com.hotservice.sauron.utils;

import android.Manifest;

/**
 * Global configurations
 */
public class Config {

    /**
     * Unique User ID
     */
    public static final String USER_ID;

    /**
     * Global Bluetooth Pin
     */
    public static final int BLUETOOTH_PIN;

    /**
     * Head for discovering related SMS messages
     */
    public static final String SMS_HEAD;

    /**
     * Needed permissions (Has to be same as in AndroidManifest.xml
     */
    public static final String[] PERMISSIONS;

    /**
     * Saves state of the App (Group creator or group client)
     */
    public static boolean CREATOR;

    static {
        CREATOR = false;

        USER_ID = StringTools.getRandomString(32);

        BLUETOOTH_PIN = 2968;

        SMS_HEAD = "|SAURON|";

        PERMISSIONS = new String[]{
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,

                Manifest.permission.READ_PHONE_STATE,

                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN,

                Manifest.permission.NFC,

                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,

                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,

                Manifest.permission.CAMERA,

                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
    }
}
