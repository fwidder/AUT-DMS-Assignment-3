package com.hotservice.sauron.utils;

import android.Manifest;

import com.google.android.gms.maps.model.LatLng;

/**
 * Global configurations
 */
public class Config {

    /**
     * Unique User ID
     */
    public static final String USER_ID;
    public static final long VIBRATION_TIME;
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
    public static final String SMS_DIV;
    public static String GROUP_NAME;
    public static String SERVER_MAC;
    /**
     * Unique User ID
     */
    public static boolean ALERT_MODE;
    public static LatLng REALLY_POINT;

    /**
     * Saves state of the App (Group creator or group client)
     */
    public static Boolean CREATOR;

    static {
        CREATOR = null;

        GROUP_NAME = null;

        ALERT_MODE = false;

        SMS_DIV = "*";

        VIBRATION_TIME = 2000;

        REALLY_POINT = new LatLng(-36.848461, 174.763336);

        USER_ID = StringTools.getRandomString(32);

        BLUETOOTH_PIN = 2968;

        SERVER_MAC = null;

        SMS_HEAD = "*SAURON*";

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
                Manifest.permission.WRITE_EXTERNAL_STORAGE,

                Manifest.permission.VIBRATE
        };
    }
}
