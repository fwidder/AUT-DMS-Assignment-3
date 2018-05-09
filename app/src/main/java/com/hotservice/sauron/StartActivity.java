package com.hotservice.sauron;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.security.Policy;

public class StartActivity extends AppCompatActivity {

    private Button create;
    private Button join;
    private Button profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_start);

        join = (Button) findViewById(R.id.JoinNav);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openJoinActivity();
            }
        });
        profile = (Button) findViewById(R.id.profileNav);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileActivity();
            }
        });
        create = (Button) findViewById(R.id.createNav);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateActivity();
            }
        });
        new networkCheck().execute();
    }

    public void openCreateActivity(){
        Intent intent = new Intent(this, CreateGroupActivity.class);
        startActivity(intent);
    }
    public void openJoinActivity(){
        Intent intent = new Intent(this, JoinActivity.class);
        startActivity(intent);
    }
    public void openProfileActivity(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
//                                                unsure    , progess,   return type
    private class networkCheck extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            return (networkInfo != null && networkInfo.isConnected());
        }
        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(b);
            if(b) actionBar.setIcon(R.drawable.iconwifi);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    private class blueToothCheck extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
            return (bta != null);
        }
        @Override
        protected void onPostExecute(Boolean b) {
            super.onPostExecute(b);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayShowHomeEnabled(b);
            if(b) actionBar.setIcon(R.drawable.blue);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }


}
