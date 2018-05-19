package com.hotservice.sauron.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Restarts the bluetooth service on destroy
 */
public class BluetoothRestarterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Bluetooth Service", "Service Stops!");

        context.startService(new Intent(context, BluetoothService.class));
    }

}
