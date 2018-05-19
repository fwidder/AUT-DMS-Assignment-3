package com.hotservice.sauron.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.hotservice.sauron.R;

import java.util.ArrayList;
import java.util.Set;

public class BlueToothActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 22;
    private BroadcastReceiver mReceiver;
    private static BluetoothAdapter bluetoothAdapter;
    public static Set<BluetoothDevice> pairedDevices;
    public static ArrayList<String> deviced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        turnOnBlue();
        makeDiscovery();
        listPaired();


        //setContentView(R.layout.activity_blue_tooth);
 /*       askTurnOnBlueTooth();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceName = device.getName();
                    String deviceHWaddress = device.getAddress();
                    Log.v("BLUEname", "device connected: "+deviceName+" MAC:"+deviceHWaddress);
                    pairedDevices.add(device);
                }
            }
        };
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); */
    }

    protected void turnOnBlue(){
        Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(intent, REQUEST_ENABLE_BT);
    }

    protected void makeDiscovery(){
        if(!bluetoothAdapter.isDiscovering()){
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BT);
        }
    }

    protected void listPaired(){
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

        deviced =  new ArrayList<>();

        for(BluetoothDevice btd : pairedDevices ){
            deviced.add(btd.getName());
        }
    }
/*    protected void askTurnOnBlueTooth(){
        BluetoothAdapter ba = BluetoothAdapter.getDefaultAdapter();
        if(!ba.isEnabled()){
            Intent enableBTintent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBTintent, REQUEST_ENABLE_BT);
        } else {
            enableDiscovery();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_ENABLE_BT){
            if(resultCode == RESULT_OK){
                enableDiscovery();
            } else {
                Toast.makeText(BlueToothActivity.this, "BlueTooth is Required!", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void enableDiscovery(){
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);
    }

    public void logPairedDevices(){
        if(pairedDevices.size() > 0){
            for (BluetoothDevice device : pairedDevices){
                Log.v("DEVICE: "+device.getName(), "MAC: "+device.getAddress());
            }
        }
    } */

}
