package com.hotservice.sauron.activities;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.messages.NFCMessage;
import com.hotservice.sauron.service.SensorService;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.MessageHelper;

/**
 * Activity to join a group
 */
public class JoinActivity extends AppCompatActivity {

    private Button scanQR;
    private Button scanNFC;

    BluetoothAdapter mBlueToothAdapter;
    Intent mServiceIntent;
    private SensorService mSensorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.CREATOR = false;

        //Set layout
        setContentView(R.layout.content_join);

        //Set context variables
        Toolbar toolbar = findViewById(R.id.toolbar);
        scanQR = findViewById(R.id.selectScan);

        setSupportActionBar(toolbar);

        //Set button listeners
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQrActivity();
            }
        });


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

    @Override
    protected void onResume() {
        super.onResume();

        //Get the intent
        Intent intent = getIntent();

        //Look up if intent is okay
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                for (Parcelable rawMsg : rawMessages) {
                    //Parse payload
                    NdefMessage message = (NdefMessage) rawMsg;
                    NFCMessage m = (NFCMessage) new MessageHelper().toMessage(message.getRecords()[0].getPayload());
                    //TODO: Do NFC Action
                    Log.d("NFC", m.toString());
                }
            }

        }
    }

    /**
     * Starts the QR-Code scan activity
     */
    public void openQrActivity() {
        Intent intent = new Intent(this, QRscanActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        stopService(mServiceIntent);
    }
}
