package com.hotservice.sauron.activities;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.messages.AbstractMessage;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.MessageHelper;

/**
 * Activity to join a group
 */
public class JoinActivity extends AppCompatActivity {

    private Button scanQR;
    private Button scanNFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.CREATOR = false;

        //Set layout
        setContentView(R.layout.content_join);

        //Set context variables
        Toolbar toolbar = findViewById(R.id.toolbar);
        scanQR = findViewById(R.id.selectScan);
        scanNFC = findViewById(R.id.selectNFC);

        setSupportActionBar(toolbar);

        //Set button listeners
        scanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQrActivity();
            }
        });
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
                    AbstractMessage m = new MessageHelper().toMessage(message.getRecords()[0].getPayload());
                    //TODO: Do NFC Action
                    Toast.makeText(this, m.toString(),
                            Toast.LENGTH_SHORT).show();
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


}
