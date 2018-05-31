package com.hotservice.sauron.service;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.hotservice.sauron.activities.MapActivity;
import com.hotservice.sauron.utils.Config;

/**
 * Listens to SMS messages and processes messages wich are in cotext with the app
 * Filter using Config.SMS_HEAD
 */
public class SmsService extends BroadcastReceiver {

    @SuppressLint("MissingPermission")
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                if (pdusObj != null)
                    for (Object msg : pdusObj) {
                        SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) msg);
                        String phoneNumber = currentMessage.getDisplayOriginatingAddress();

                        String message = currentMessage.getDisplayMessageBody();

                        if (!message.startsWith(Config.SMS_HEAD))
                            return;

                        //Set Point
                        String tmp = message.substring(message.indexOf(Config.SMS_DIV) + 1);
                        tmp = tmp.substring(tmp.indexOf(Config.SMS_DIV) + 1);
                        String lat = tmp.substring(0, tmp.lastIndexOf(Config.SMS_DIV));
                        String lon = tmp.substring(tmp.lastIndexOf(Config.SMS_DIV) + 1, tmp.length());
                        LatLng reallyPoint = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
                        Config.REALLY_POINT = reallyPoint;

                        //Vibrate
                        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            v.vibrate(VibrationEffect.createOneShot(Config.VIBRATION_TIME, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            v.vibrate(Config.VIBRATION_TIME);
                        }

                        //PopUp
                        int duration = Toast.LENGTH_LONG;
                        Log.d(getClass().getSimpleName(), reallyPoint.toString());
                        Toast toast = Toast.makeText(context,
                                "sender: \"" + phoneNumber + "\"," + reallyPoint.toString(), duration);
                        toast.show();

                        //Open Activity
                        Config.ALERT_MODE = true;
                        Intent act = new Intent(context, MapActivity.class);
                        context.startActivity(act);
                    }
            }
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception in SmsListener: " + e);
        }
    }
}