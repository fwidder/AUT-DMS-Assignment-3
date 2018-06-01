package com.hotservice.sauron.activities;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.model.messages.NFCMessage;
import com.hotservice.sauron.service.SensorService;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.NfcMessageWrapper;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.UUID;

/**
 * Activity to create a group
 */
public class CreateGroupActivity extends AppCompatActivity {

    private static final UUID MY_UUID = UUID.randomUUID();

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
    BluetoothAdapter mBlueToothAdapter;
    Intent mServiceIntent;
    private Button saveButton;
    private EditText nameBox;
    private ImageView imageView;
    private SensorService mSensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.CREATOR = true;
        // Set layout
        setContentView(R.layout.activity_create_group);


        // Set context variables
        Toolbar toolbar = findViewById(R.id.toolbar);
        nameBox = this.findViewById(R.id.nameBox);
        imageView = this.findViewById(R.id.createGroupQR);
        saveButton = this.findViewById(R.id.saveButton);
        Button btAdd = findViewById(R.id.addDevice);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(CreateGroupActivity.this, JoinBlueActivity.class);
                startActivity(intent);
            }
        });
        setSupportActionBar(toolbar);

        // Set button actions
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.GROUP_NAME = nameBox.getText().toString();
                // Set to CREATOR (Server State)
                Config.CREATOR = true;

                // Create and display QR-Code
                String text2QR = "Error";
                try {
                    text2QR = Group.getInstance().getMe().getBTMac();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            }
        });


        if (Config.GROUP_NAME != null) {
            nameBox.setText(Config.GROUP_NAME);
            saveButton.performClick();
        }
        mSensorService = new SensorService(this);
        mServiceIntent = new Intent(this, mSensorService.getClass());
        if (!isMyServiceRunning(mSensorService.getClass())) {
            startService(mServiceIntent);
        }

    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i("isMyServiceRunning?", true + "");
                return true;
            }
        }
        Log.i("isMyServiceRunning?", false + "");
        return false;
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

        try {
            message = new NfcMessageWrapper(new NFCMessage().setMac(Group.getInstance().getMe().getBTMac()).setName(Group.getInstance().getMe().getName()));

            nfcAdapter.setNdefPushMessageCallback(message, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(mServiceIntent);
    }
}
