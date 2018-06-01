package com.hotservice.sauron.service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hotservice.sauron.activities.MapActivity;
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.model.User;
import com.hotservice.sauron.utils.Config;

import java.io.IOException;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

/**
 * Created by fabio on 30/01/2016.
 */
public class SensorService extends Service {
    private final Context context;
    public int counter = 0;
    long oldTime = 0;
    private Timer timer;
    private TimerTask timerTask;

    public SensorService(Context applicationContext) {
        super();
        this.context = applicationContext;
        Log.i("HERE", "here I am!");
    }

    public SensorService() {
        context = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("EXIT", "ondestroy!");
        Intent broadcastIntent = new Intent("com.hotservice.sauron.RestartSensor");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 10000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                Log.i("in timer", "in timer ++++  " + (counter++));
                if (Config.CREATOR == null) {
                    stoptimertask();
                    return;
                }
                if (Config.CREATOR) {
                    BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
                    // Phone does not support Bluetooth so let the user know and exit.
                    if (BTAdapter == null) {
                        //No BT
                    }
                    if (!BTAdapter.isEnabled()) {
                        //Error
                    }
                    Set<BluetoothDevice> pairedDevices = BTAdapter.getBondedDevices();
                    Log.d("BT TEST", pairedDevices.size() + "");
                    for (BluetoothDevice bt : pairedDevices) {
                        boolean found = false;
                        for (User u : Group.getInstance().getUserList())
                            if (u.getBTMac().toLowerCase().equals(bt.getAddress().toLowerCase()))
                                found = true;
                        if (!found)
                            continue;
                        Boolean ret = connect(bt, UUID.randomUUID());
                        if (!ret) {
                            stoptimertask();
                            Intent intent = new Intent(SensorService.this, MapActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        Log.d("BT TEST", bt.getAddress() + " - " + bt.getName() + ":" + ret.toString());
                    }
                } else {
                    BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
                    // Phone does not support Bluetooth so let the user know and exit.
                    if (BTAdapter == null) {
                        //No BT
                    }
                    if (!BTAdapter.isEnabled()) {
                        //Error
                    }
                    try {
                        BTAdapter.listenUsingRfcommWithServiceRecord("Hai", UUID.fromString("550e8400-e29b-11d4-a716-446655440055")).accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        };


    }

    public boolean connect(BluetoothDevice bTDevice, UUID mUUID) {
        try {
            BluetoothSocket tmp = bTDevice.createRfcommSocketToServiceRecord(UUID.fromString("550e8400-e29b-11d4-a716-446655440055"));
            tmp.connect();
            tmp.close();
        } catch (IOException e) {
            //e.printStackTrace();
            return false;
        }
        return true;

    }

    /**
     * not needed
     */
    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}