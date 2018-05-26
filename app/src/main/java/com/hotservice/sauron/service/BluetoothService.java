package com.hotservice.sauron.service;

import android.app.ProgressDialog;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.hotservice.sauron.utils.BluetoothConnection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class BluetoothService extends Service {
    public int counter = 0;
    long oldTime = 0;
    private Timer timer;
    private TimerTask timerTask;
    private static final String TAG = "BluetoothConnectionService";
    private static final String appName = "sauron";
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private final BluetoothAdapter mBlueToothAdapter;
    Context context;

    private AcceptThread mInsucureAcceptThread;
    private ConnectThread mConnectThread;
    private BluetoothDevice mmDevice;
    private UUID deviceUUID;
    ProgressDialog mProgressDialog;
    private ConnectedThread mConnectedThread;


    public BluetoothService(Context applicationContext) {
        super();
        this.context = applicationContext;
        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        Log.d("Bluetooth Service", "Service created!");
        start();
    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;

        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mBlueToothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID);
                Log.d("AcceptThread", "Server running on " + MY_UUID);
            } catch (IOException e) {
                Log.d("AcceptThread", "Failed to start server on  " + e);
            }
            mmServerSocket = tmp;
        }

        public void run() {
            BluetoothSocket socket = null;
            try {
                socket = mmServerSocket.accept();
                Log.d("RUN", "RFCOM server socket accepted a connection");
            } catch (IOException ioe) {
                Log.d("AcceptThread", "Failed to start server on  " + ioe);
            }
            if (socket != null) {
                connected(socket, mmDevice);
            }
            Log.i("END", "Accpted a Connection");
        }

        public void cancel() {
            Log.d("Cancelling", "Accept thread cancelled");
            try {
                mmServerSocket.close();
            } catch (IOException e) {
                Log.e("Cancel", "Closing the accept thread has failed");
            }
        }
    }

        /* this is the connectors thread either sucsessful or fails badly  */
        private class ConnectThread extends Thread {
            private BluetoothSocket mmSocket;

            public ConnectThread(BluetoothDevice device, UUID uuid) {
                mmDevice = device;
                deviceUUID = uuid;
            }

            public void run() {
                BluetoothSocket tmp = null;
                Log.i("RUN", "Connect thread");
                try {
                    Log.i("RUN", "Trying to create a socket using UUID: " + MY_UUID);
                    tmp = mmDevice.createRfcommSocketToServiceRecord(deviceUUID);
                } catch (IOException e) {
                    Log.i("RUN", "Failed to create a socket using UUID: " + MY_UUID);
                }
                mmSocket = tmp;
                mBlueToothAdapter.cancelDiscovery();
                try {
                    mmSocket.connect();
                    Log.i("RUN", "Connect thread connected to: " + MY_UUID);
                } catch (IOException e) {
                    try {
                        mmSocket.close();
                        Log.i("RUN", "Close socket to: " + MY_UUID);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Log.i("RUN", "Cant connect to: " + MY_UUID);
                }
                connected(mmSocket, mmDevice);
            }

            public void cancel() {
                try {
                    Log.i("cancel", "closing client socket to : " + MY_UUID);
                    mmSocket.close();
                } catch (IOException e) {
                    Log.i("cancel", "cancel failed mmSocket close failed: " + MY_UUID);
                }
            }
        }


    public synchronized void start() {
        Log.i("START", "Start: ");
        if (mConnectThread != null) {
            mConnectThread.cancel();
            mConnectThread = null;
        }
        if(mInsucureAcceptThread == null){
            mInsucureAcceptThread = new AcceptThread();
            mInsucureAcceptThread.start();
        }
    }

    public void startClient(BluetoothDevice device, UUID uuid){
            mProgressDialog = ProgressDialog.show(context, " Connecting BlueTooth", "Please Wait...", true);
            mConnectThread = new ConnectThread(device, uuid);
            mConnectThread.start();
    }

    private class ConnectedThread extends  Thread {
            private final BluetoothSocket mmSocket;
            private final InputStream mmInStream;
            private final OutputStream mmOutStream;
            public ConnectedThread(BluetoothSocket socket){
                mmSocket = socket;
                InputStream tempIn = null;
                OutputStream tempOut = null;

                try{
                    mProgressDialog.dismiss();
                }catch(NullPointerException np){
                    np.printStackTrace();
                }
                try {
                    tempIn = mmSocket.getInputStream();
                    tempOut = mmSocket.getOutputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mmInStream = tempIn;
                mmOutStream = tempOut;
            }
            public void run(){
                byte[] buffer = new byte[1024];
                int bytes;
                while(true){
                    try{
                        bytes = mmInStream.read(buffer);
                        String incomingMessage = new String(buffer, 0 , bytes);
                        Log.i("IN message", ""+incomingMessage);
                    } catch (IOException e){
                        e.printStackTrace();
                        break;
                    }
                }
            }
            public void write(byte[] bytes){
                String text = new String(bytes, Charset.defaultCharset());
                Log.i("sending", "msg: "+text);
                try{
                    mmOutStream.write(bytes);
                }catch(IOException e){
                    e.printStackTrace();
                }
            }

            public void cancel(){
                try{
                    mmSocket.close();
                } catch(IOException e){
                    e.printStackTrace();
                }
            }
    }

    public void connected(BluetoothSocket mmSocket, BluetoothDevice mmDevice){
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
    }

    public void write(byte[] out){
            ConnectedThread r;
            mConnectedThread.write(out);
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
        Log.i("Bluetooth Service", "Service destroyed!");
        Intent broadcastIntent = new Intent("com.hotservice.sauron.service.BluetoothRestart");
        sendBroadcast(broadcastIntent);
        stoptimertask();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();

        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, to wake up every 1 second
        timer.schedule(timerTask, 1000, 1000); //
    }

    /**
     * it sets the timer to print the counter every x seconds
     */
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {

                Log.i("Bluetooth Service", "Service running: " + (counter++));
            }
        };
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