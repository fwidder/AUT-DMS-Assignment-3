package com.hotservice.sauron.activities;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.messages.NfcMessage;

public class NFCscanActivity extends AppCompatActivity {

    private NfcAdapter nfcAdapter;
    private NfcMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfcscan);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "Sorry this device does not have NFC.", Toast.LENGTH_LONG).show();
            return;
        }

        if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_LONG).show();
        }

        //TODO: Create Message

        message = new NfcMessage("TestMe");

        nfcAdapter.setNdefPushMessageCallback(message, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        String re;
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            re = new String(message.getRecords()[0].getPayload());

        } else
            re = "Waiting for NDEF Message";
        Toast.makeText(this, re, Toast.LENGTH_LONG).show();
    }
}


