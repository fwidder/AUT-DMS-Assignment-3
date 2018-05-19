package com.hotservice.sauron.activities;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hotservice.sauron.R;

/**
 * Activity to join a group
 */
public class JoinActivity extends AppCompatActivity {

    private Button scanQR;
    private Button scanNFC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set context variables
        Toolbar toolbar = findViewById(R.id.toolbar);
        scanQR = findViewById(R.id.selectScan);
        scanNFC = findViewById(R.id.selectNFC);

        //Set layout
        setContentView(R.layout.content_join);
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
                    String s = new String(message.getRecords()[0].getPayload());
                    //TODO: Do NFC Action
                    Toast.makeText(this, s,
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
