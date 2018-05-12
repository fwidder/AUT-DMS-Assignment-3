package com.hotservice.sauron.utils;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BlueTimer extends IntentService {

    public BlueTimer() {
        super("BlueTooth Timer Service");
    }

    public void onCreate() {
        super.onCreate();
        Log.v("timer", "Timer service has started");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 5; i++) {
            Log.v("timer", "i = " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
