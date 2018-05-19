package com.hotservice.sauron.utils;

import android.app.IntentService;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.hotservice.sauron.activities.BlueToothActivity;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;

public class BlueTimer extends IntentService {

    public static Set<BluetoothDevice> currentDevices = BlueToothActivity.pairedDevices;
    private BroadcastReceiver mReceiver;
    BluetoothAdapter ba;

    public BlueTimer() {
        super("BlueTooth Timer Service");
    }

    public void onCreate() {
        super.onCreate();
        ba = BluetoothAdapter.getDefaultAdapter();
        Log.v("timer", "Timer service has started");
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter);
    }

  /*  private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName();
                String deviceHardwareAddress = device.getAddress();
// MAC address
            }
        }
    }; */

    @Override
    protected void onHandleIntent(Intent intent) {

        for (int i = 0; i < 1000; i++) {
            Log.v("timer", "i = " + i);
            LogDevices(BlueToothActivity.deviced);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    protected void LogDevices(ArrayList<String> devices){
        if(devices != null){
            if(devices.size() > 0){
                for (String device : devices){
                    Log.v("Blue Name ", ""+device);
                }
            }
        }
    }

    protected void doSomthing(){

    }

}
