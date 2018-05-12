package com.hotservice.sauron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

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
        nfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNfcActivity();
            }
        });
    }

    public void openQrActivity() {
        Intent intent = new Intent(this, QRscanActivity.class);
        startActivity(intent);
    }

    public void openNfcActivity() {
        Intent intent = new Intent(this, NFCscanActivity.class);
        startActivity(intent);
    }
}
