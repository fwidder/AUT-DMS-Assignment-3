package com.hotservice.sauron.utils;


import android.bluetooth.BluetoothAdapter;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.hotservice.sauron.R;

import java.lang.ref.WeakReference;
                                              // context     progress   return type  //
public class BluetoothChecker extends AsyncTask<AppCompatActivity, Void, Boolean> {
    private WeakReference<AppCompatActivity> activity;

    @Override
    protected Boolean doInBackground(AppCompatActivity... activities) {
        this.activity = new WeakReference<>(activities[0]);
        BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
        return (bta != null);
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        ActionBar actionBar = activity.get().getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayShowHomeEnabled(b);
        if (b && actionBar != null) actionBar.setIcon(R.drawable.blue);
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