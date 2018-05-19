package com.hotservice.sauron.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hotservice.sauron.R;
import com.hotservice.sauron.model.Group;
import com.hotservice.sauron.model.User;
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

        // Start Set User
        User u = new User();
        u.setID(Config.USER_ID);
        Group.getInstance().getUserList().add(u);
        Log.d("User ID", Config.USER_ID);
        //End Set User

        // Set Layout
        setContentView(R.layout.content_start);

        // Set context variables
        join = findViewById(R.id.JoinNav);
        profile = findViewById(R.id.profileNav);
        create = findViewById(R.id.createNav);
        mRequestPermissionHandler = new RequestPermissionHandler();

        // Set Button listeners
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinActivity();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateActivity();
            }
        });
        //Get permissions
        loadPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Call permission Handler
        mRequestPermissionHandler.onRequestPermissionsResult(requestCode, permissions,
                grantResults);
    }

    /**
     * Loads the permissions specified in Config.class
     */
    private void loadPermissions() {
        mRequestPermissionHandler.requestPermission(this, Config.PERMISSIONS, 12345, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(StartActivity.this, "request permission success!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed() {
                //Exit when permission request fails
                Toast.makeText(StartActivity.this, "request permission failed", Toast.LENGTH_LONG).show();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.d("ERROR", e.toString());
                }
                System.exit(1);
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
