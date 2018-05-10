package com.hotservice.sauron.utils;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

public class PhoneNumberChecker extends AsyncTask<AppCompatActivity, Void, String> {

    @Override
    protected String doInBackground(AppCompatActivity... appCompatActivities) {
        //TelephonyManager tMgr = (TelephonyManager) AppContext.getSystemService(Context.TELEPHONY_SERVICE);
        //String mPhoneNumber = tMgr.getLine1Number();
        return "";
    }
}
