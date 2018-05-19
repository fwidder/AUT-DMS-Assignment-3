package com.hotservice.sauron.activities;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hotservice.sauron.R;
import com.hotservice.sauron.service.BluetoothService;

public class BlueToothActivity extends AppCompatActivity {
    Intent mServiceIntent;
    Context ctx;
    private BluetoothService mBluetoothService;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_blue_tooth);
        mBluetoothService = new BluetoothService(getCtx());
        mServiceIntent = new Intent(getCtx(), mBluetoothService.getClass());
        if (!isMyServiceRunning(mBluetoothService.getClass())) {
            Log.d("Bluetooth Service", "Starting Service");
            startService(mServiceIntent);
        }
    }


    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d("Bluetooth Service", "running: " + true + "");
                return true;
            }
        }
        Log.d("Bluetooth Service", "running: " + false + "");
        return false;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("Bluetooth Service", "Activity destroy!");
        super.onDestroy();
    }
}
