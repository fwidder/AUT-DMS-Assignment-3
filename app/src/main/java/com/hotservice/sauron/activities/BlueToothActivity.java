package com.hotservice.sauron.activities;

import android.Manifest;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.hotservice.sauron.R;
import com.hotservice.sauron.service.BluetoothService;
import com.hotservice.sauron.service.DeviceListAdapter;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.UUID;

public class BlueToothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();//Collections.synchronizedList(new ArrayList());
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;
    Button btnDiscover;
    Button discoverbtn;
    Button startConnection;
    Button send;
    EditText editText2;
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    BluetoothDevice mBTDevice;
    BluetoothAdapter mBlueToothAdapter;
    Intent mServiceIntent;
    Context ctx;
    BluetoothService mBluetoothConnection;


    private static BluetoothService mBluetoothService;

    public Context getCtx() {
        return ctx;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_blue_tooth);
        discoverbtn = findViewById(R.id.discoverbtn);
        discoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDiscover(v);
            }
        });
        btnDiscover = findViewById(R.id.btnDiscoverable_on_off);
        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDiscoveable(v);
            }
        });
        lvNewDevices = findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        startConnection = findViewById(R.id.startConnection);
        startConnection.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startConnection();
            }
        });
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                byte[] bytes = editText2.getText().toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(bytes);
            }
        });
        editText2 = findViewById(R.id.editText2);

        IntentFilter pairFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver3, pairFilter);

        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvNewDevices.setOnItemClickListener(BlueToothActivity.this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver, filter);



        mBluetoothService = new BluetoothService(getCtx());

/*        mServiceIntent = new Intent(getCtx(), mBluetoothService.getClass());
        if (!isMyServiceRunning(mBluetoothService.getClass())) {

            Log.d("Bluetooth Service", "Starting Service");
            startService(mServiceIntent);
        } */
    }

    public void startConnection(){
        startBTConnection(mBTDevice, MY_UUID);
    }
    public void startBTConnection(BluetoothDevice device, UUID uuid){
        Log.d("transfer", "Initalizing RFCOM Bluetooth Connection.");
        mBluetoothConnection.startClient(device,uuid);
    }


  /*  private boolean isMyServiceRunning(Class<?> serviceClass) {

        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : Objects.requireNonNull(manager).getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.d("Bluetooth Service", "running: " + true + "");
                return true;
            }
        }
        Log.d("Bluetooth Service", "running: " + false + "");
        return false;
    } */

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        Log.i("Bluetooth Service", "Activity destroy!");
        //unregisterReceiver(mBroadcastReceiver);
        //unregisterReceiver(mBroadcastReceiver2);
        //unregisterReceiver(mBroadcastReceiver3);
        super.onDestroy();
    }

    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch(mode){
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d("mBroadcastReciever", "Discovery Enabled" );
                        break;
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
                        Log.d("mBroadcastReciever", "Discovery Disabled Able to receive connections");
                        break;
                    case BluetoothAdapter.SCAN_MODE_NONE:
                        Log.d("mBroadcastReciever", "Discovery Disabled Unable to receive connections");
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        Log.d("mBroadcastReciever", "Connecting.....");
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        Log.d("mBroadcastReciever", "Connected.");
                        break;
                }
            }
        }
    };

    private BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("mBroadcastReciver2", "ACTION found");

            if(action.equals(BluetoothDevice.ACTION_FOUND)){
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d("onRecieve", ""+device.getName()+" : "+device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);

            }
        }
    };

    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if(action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)){
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if(mDevice.getBondState() == BluetoothDevice.BOND_BONDED){
                    Log.d("Bonding", "BOND_BONDED.");
                    mBTDevice = mDevice;
                }
                if(mDevice.getBondState() == BluetoothDevice.BOND_BONDING){
                    Log.d("Bonding", "BOND_BONDING.");
                }
                if(mDevice.getBondState() == BluetoothDevice.BOND_NONE){
                    Log.d("Bonding", "BOND_NONE.");
                }
            }
        }
    };

    public void enableDiscoveable(View view){
        Log.d("enanble bluetooth", "Making discoverable for 300 seconds");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(mBlueToothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }



    public void btnDiscover(View view){
        Log.d("discover", "looking for paired devices");
        if(mBlueToothAdapter.isDiscovering()){
            mBlueToothAdapter.cancelDiscovery();
            // check permissions in manifest
            checkPermissions();

            mBlueToothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }
        if(!mBlueToothAdapter.isDiscovering()){
            checkPermissions();
            mBlueToothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }
    }

    private void checkPermissions(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
            permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
            if(permissionCheck != 0){
                this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001 );
            }
        }else {
            Log.d("checkBTPermissions", "SDK Version < Lollipop");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mBlueToothAdapter.cancelDiscovery();
        Log.d("onItemCick", "clicked a device");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAdress = mBTDevices.get(i).getAddress();

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2){
            Log.d("pairing with: ", "name: "+deviceName+" : "+deviceAdress);
            mBTDevices.get(i).createBond();
            mBTDevice = mBTDevices.get(i);
            mBluetoothConnection = new BluetoothService(BlueToothActivity.this);
        }
    }
}
