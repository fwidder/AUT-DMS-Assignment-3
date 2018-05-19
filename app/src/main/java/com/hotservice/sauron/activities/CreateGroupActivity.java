package com.hotservice.sauron.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import com.hotservice.sauron.model.messages.NfcMessage;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class CreateGroupActivity extends AppCompatActivity {

    private Button button;
    private EditText editText;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this;
        editText = this.findViewById(R.id.nameBox);
        imageView = this.findViewById(R.id.createGroupQR);
        button = this.findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text2QR = editText.getText().toString();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 400, 400);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                sendMessage();
            }
        });

    }

    private void sendMessage() {
        NfcAdapter nfcAdapter;
        NfcMessage message;
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        PackageManager pm = this.getPackageManager();
        if (!pm.hasSystemFeature(PackageManager.FEATURE_NFC)) {
            Toast.makeText(this, "@String/noNfc",
                    Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this, "@String/nfwDisabled", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFC_SETTINGS));
        } else if (!nfcAdapter.isNdefPushEnabled()) {
            Toast.makeText(this, "@String/androidBeamDIsabled",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }

        //TODO: Create Message

        message = new NfcMessage("TestMe");

        nfcAdapter.setNdefPushMessageCallback(message, this);
    }

}
