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

public class JoinActivity extends AppCompatActivity {

    private Button qr;
    private Button nfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_join);                                // set view to layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        qr = findViewById(R.id.selectScan);
        nfc = findViewById(R.id.selectNFC);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQrActivity();
            }
        });
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
                    //TODO: Do NFC Action
                    Toast.makeText(this, s,
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    public void openQrActivity() {
        Intent intent = new Intent(this, QRscanActivity.class);
        startActivity(intent);
    }
}
