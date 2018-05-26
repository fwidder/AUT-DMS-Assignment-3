package com.hotservice.sauron.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hotservice.sauron.R;
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.model.User;
import com.hotservice.sauron.utils.Config;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private MapView mapView;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);

        findViewById(R.id.btSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.setOnMapClickListener(null);
                //TODO: Send SMS
                SmsManager smsManager = SmsManager.getDefault();
                for (User u : Group.getInstance().getUserList()) {
                    try {
                        if (u.equals(Group.getInstance().getMe()))
                            continue;
                        smsManager.sendTextMessage(u.getMobileNumber(), null, (Config.SMS_HEAD + Config.REALLY_POINT.latitude + "|" + Config.REALLY_POINT.longitude), null, null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        mapView.getMapAsync(this);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(0, 0)));
        //TODO: if(Config.CREATOR)
        map.setOnMapClickListener(this);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapClick(LatLng latLng) {
        Config.REALLY_POINT = latLng;
        map.clear();
        map.addMarker(new MarkerOptions().position(Config.REALLY_POINT)
                .title("Really Point"));
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));

    }
}
