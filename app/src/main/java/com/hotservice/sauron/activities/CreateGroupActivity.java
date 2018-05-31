package com.hotservice.sauron.activities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hotservice.sauron.R;
import com.hotservice.sauron.model.messages.AbstractMessage;
import com.hotservice.sauron.service.BluetoothService;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.NfcMessageWrapper;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.UUID;

/**
 * Activity to create a group
 */
public class CreateGroupActivity extends AppCompatActivity {

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
    private Button saveButton;
    private EditText nameBox;
    private ImageView imageView;
    BluetoothAdapter mBlueToothAdapter;
    private static BluetoothService mBluetoothService;
    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");
    BluetoothService mBluetoothConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.CREATOR = true;
        mBlueToothAdapter = BluetoothAdapter.getDefaultAdapter();
        mBluetoothService = new BluetoothService(this);
        mBluetoothConnection = new BluetoothService(CreateGroupActivity.this);
        // Set layout
        setContentView(R.layout.activity_create_group);

        // Set context variables
        Toolbar toolbar = findViewById(R.id.toolbar);
        nameBox = this.findViewById(R.id.nameBox);
        imageView = this.findViewById(R.id.createGroupQR);
        saveButton = this.findViewById(R.id.saveButton);
        startConnection();

        setSupportActionBar(toolbar);

        // Set button actions
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set to CREATOR (Server State)
                Config.CREATOR = true;

                // Create and display QR-Code
                String text2QR = nameBox.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 400, 400);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                //Start NFC-Sending
                startNFC();

                enableDiscoveable();
            }
        });
    }

    /**
     * Starts NFC sending process
     */
    private void startNFC() {
        NfcAdapter nfcAdapter;
        NfcMessageWrapper message;
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        PackageManager pm = this.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(this, "@String/noNfc",
                    Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "@String/nfwDisabled", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(this, "@String/androidBeamDisabled",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }

        //TODO: Create Message

        message = new NfcMessageWrapper(new AbstractMessage() {
        });

        nfcAdapter.setNdefPushMessageCallback(message, this);
    }

    private final BroadcastReceiver mBroadcastReceiver2 = new BroadcastReceiver() {
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

    public void enableDiscoveable() {
        Log.d("enanble bluetooth", "Making discoverable for 300 seconds");

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(discoverableIntent);

        IntentFilter intentFilter = new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        registerReceiver(mBroadcastReceiver2, intentFilter);
    }

    public void startConnection() {
        startBTConnection(null, MY_UUID);           //  server dosnt neeed a device to listen for?
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d("transfer", "Initalizing RFCOM Bluetooth Connection.");
        mBluetoothConnection.startClient(device, uuid);
    }
}
