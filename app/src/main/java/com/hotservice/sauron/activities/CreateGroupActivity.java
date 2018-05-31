package com.hotservice.sauron.activities;

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
import com.hotservice.sauron.model.messages.AbstractMessage;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.NfcMessageWrapper;
import com.journeyapps.barcodescanner.BarcodeEncoder;

/**
 * Activity to create a group
 */
public class CreateGroupActivity extends AppCompatActivity {

    private Button saveButton;
    private EditText nameBox;
    private ImageView imageView;

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

}
