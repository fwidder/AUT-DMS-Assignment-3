package com.hotservice.sauron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.google.zxing.Result;
import com.hotservice.sauron.R;
import com.hotservice.sauron.utils.Config;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRscanActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set context variables
        scannerView = new ZXingScannerView(this);
        Toolbar toolbar = findViewById(R.id.toolbar);

        //Set layout
        setContentView(scannerView);

        setSupportActionBar(toolbar);

    }

    @Override
    public void onResume() {
        super.onResume();
        //Initialize scanner
        if (scannerView == null) {
            scannerView = new ZXingScannerView(this);
            setContentView(scannerView);
        }

        //Configure scanner
        scannerView.setResultHandler(this);

        //Start scanning
        scannerView.startCamera();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Stop scanning
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        //TODO: Handle result of QR Scan
        final String scanResult = result.getText();
        Config.SERVER_MAC = scanResult;
        Log.d("SCANNED", "" + scanResult);
    /*    AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                scannerView.resumeCameraPreview(QRscanActivity.this);

            }
        });
        builder.setNeutralButton("Visit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();   */
        scannerView.stopCamera();
        Intent intent = new Intent(this, JoinBlueActivity.class);
        startActivity(intent);

    }

}