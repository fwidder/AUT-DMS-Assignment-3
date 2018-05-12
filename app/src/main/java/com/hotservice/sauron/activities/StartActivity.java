package com.hotservice.sauron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hotservice.sauron.R;
import com.hotservice.sauron.utils.Config;
import com.hotservice.sauron.utils.RequestPermissionHandler;

public class StartActivity extends AppCompatActivity {

    private Button create;
    private Button join;
    private Button profile;
    private RequestPermissionHandler mRequestPermissionHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRequestPermissionHandler = new RequestPermissionHandler();

        setContentView(R.layout.content_start);

        join = findViewById(R.id.JoinNav);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinActivity();
            }
        });
        profile = findViewById(R.id.profileNav);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });
        create = findViewById(R.id.createNav);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateActivity();
            }
        });

        loadPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    private void loadPermissions() {
        mRequestPermissionHandler.requestPermission(this, Config.PERMISSIONS, 12345, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(StartActivity.this, "request permission success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailed() {
                Toast.makeText(StartActivity.this, "request permission failed", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void openCreateActivity() {
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }

    public void openJoinActivity() {
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }

    public void openProfileActivity() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
}
