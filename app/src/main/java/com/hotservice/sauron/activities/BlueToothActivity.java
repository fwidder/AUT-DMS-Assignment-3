package com.hotservice.sauron.activities;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.model.messages.BluetoothMessage;
import com.hotservice.sauron.service.BluetoothService;
import com.hotservice.sauron.service.DeviceListAdapter;
import com.hotservice.sauron.utils.MessageHelper;

import java.util.ArrayList;
import java.util.UUID;

public class BlueToothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)) {
                int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);

                switch (mode) {
                    case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
                        Log.d("mBroadcastReciever", "Discovery Enabled");
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
    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();//Collections.synchronizedList(new ArrayList());
    public DeviceListAdapter mDeviceListAdapter;
    ListView lvNewDevices;
    Button btnDiscover;
    Button discoverbtn;
    Button startConnection;
    Button send;
    TextView connectionState;
    BluetoothDevice mBTDevice;
    private final BroadcastReceiver mBroadcastReceiver3 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                BluetoothDevice mDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDED) {
                    Log.d("Bonding", "BOND_BONDED.");
                    mBTDevice = mDevice;
                }
                if (mDevice.getBondState() == BluetoothDevice.BOND_BONDING) {
                    Log.d("Bonding", "BOND_BONDING.");
                }
                if (mDevice.getBondState() == BluetoothDevice.BOND_NONE) {
                    Log.d("Bonding", "BOND_NONE.");
                }
            }
        }
    };
    BluetoothAdapter mBlueToothAdapter;
    Intent mServiceIntent;
    Context ctx;
    BluetoothService mBluetoothConnection;
    private BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            Log.d("mBroadcastReciver2", "ACTION found");

            if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                mBTDevices.add(device);
                Log.d("onRecieve", "" + device.getName() + " : " + device.getAddress());
                mDeviceListAdapter = new DeviceListAdapter(context, R.layout.device_adapter_view, mBTDevices);
                lvNewDevices.setAdapter(mDeviceListAdapter);

            }
        }
    };

    public Context getCtx() {
        return ctx;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_blue_tooth);
        mBTDevices = new ArrayList<>();
        lvNewDevices = findViewById(R.id.lvNewDevices);
        btnDiscover = findViewById(R.id.btnDiscoverable_on_off);
        btnDiscover.setEnabled(true);
        discoverbtn = findViewById(R.id.discoverbtn);
        discoverbtn.setEnabled(false);
        startConnection = findViewById(R.id.startConnection);
        startConnection.setEnabled(false);
        connectionState.findViewById(R.id.connectionState);
        send = findViewById(R.id.send);
        send.setEnabled(false);

        btnDiscover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enableDiscoveable();
                btnDiscover.setEnabled(false);
                discoverbtn.setEnabled(true);
            }
        });

        discoverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDiscover();
                discoverbtn.setEnabled(false);
            }
        });

        startConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
                startConnection.setEnabled(false);
                send.setEnabled(true);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                byte[] msg = new byte[0];
                try {
                    msg = new MessageHelper().toBytes(new BluetoothMessage("2345234534", Group.getInstance().getUserList()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //byte[] bytes = editText2.getText().toString().getBytes(Charset.defaultCharset());
                mBluetoothConnection.write(msg);
            }
        });

        IntentFilter pairFilter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        registerReceiver(mBroadcastReceiver3, pairFilter);

        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        lvNewDevices.setOnItemClickListener(BlueToothActivity.this);

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mBroadcastReceiver, filter);

    }

    public void startConnection() {
        startBTConnection(mBTDevice, MY_UUID);
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d("transfer", "Initalizing RFCOM Bluetooth Connection.");
        mBluetoothConnection.startClient(device, uuid);
    }

    @Override
    protected void onDestroy() {
        //stopService(mServiceIntent);
        //Log.i("Bluetooth Service", "Activity destroy!");
        unregisterReceiver(mBroadcastReceiver);
        unregisterReceiver(mBroadcastReceiver2);
        unregisterReceiver(mBroadcastReceiver3);
        super.onDestroy();
    }

    public void enableDiscoveable() {
        Log.d("enanble bluetooth", "Making discoverable for 300 seconds");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }


    public void btnDiscover() {
        Log.d("discover", "looking for paired devices");
        if (mBlueToothAdapter.isDiscovering()) {
            mBlueToothAdapter.cancelDiscovery();
            // check permissions in manifest
            checkPermissions();

            mBlueToothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }
        if (!mBlueToothAdapter.isDiscovering()) {
            checkPermissions();
            mBlueToothAdapter.startDiscovery();
            IntentFilter discoverDevicesIntent = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            registerReceiver(mBroadcastReceiver2, discoverDevicesIntent);
        }
    }

    private void checkPermissions() {
        int permissionCheck = this.checkSelfPermission("Manifest.permission.ACCESS_FINE_LOCATION");
        permissionCheck += this.checkSelfPermission("Manifest.permission.ACCESS_COARSE_LOCATION");
        if (permissionCheck != 0) {
            this.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1001);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mBlueToothAdapter.cancelDiscovery();
        Log.d("onItemCick", "clicked a device");
        String deviceName = mBTDevices.get(i).getName();
        String deviceAdress = mBTDevices.get(i).getAddress();

        Log.d("pairing with: ", "name: " + deviceName + " : " + deviceAdress);
        mBTDevices.get(i).createBond();
        mBTDevice = mBTDevices.get(i);
        mBluetoothConnection = new BluetoothService(BlueToothActivity.this);
        startConnection.setEnabled(true);
    }
}
