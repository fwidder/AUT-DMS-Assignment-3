package com.hotservice.sauron.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.hotservice.sauron.utils.Config;

public class SmsListener extends BroadcastReceiver {

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

                        int duration = Toast.LENGTH_LONG;
                        Toast toast = Toast.makeText(context,
                                "sender: \"" + phoneNumber + "\", message: \"" + message + "\"", duration);
                        toast.show();
                    }
            }
        } catch (Exception e) {
            Log.e("SmsListener", "Exception in SmsListener: " + e);
        }
    }
}