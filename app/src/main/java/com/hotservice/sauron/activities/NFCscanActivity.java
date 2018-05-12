package com.hotservice.sauron.activities;

//TODO: Move to Join Activity
import android.content.Intent;
import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
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

        sendMessage();
    }

    private void sendMessage() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        PackageManager pm = this.getPackageManager();
        //TODO: Remove Hardcoded Strings
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(this, "The device does not has NFC hardware.",
                    Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "Please enable NFC via Settings.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(this, "Please enable Android Beam.",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
        //TODO: Create Message
        message = new NfcMessage("TestMe");
        nfcAdapter.setNdefPushMessageCallback(message, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                for (Parcelable rawMsg : rawMessages) {
                    NdefMessage message = (NdefMessage) rawMsg;
                    String s = new String(message.getRecords()[0].getPayload());
                    Toast.makeText(this, s,
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}