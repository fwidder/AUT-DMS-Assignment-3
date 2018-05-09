package com.hotservice.sauron.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.hotservice.sauron.R;

import java.lang.ref.WeakReference;

public class WifiChecker extends AsyncTask<AppCompatActivity, Void, Boolean> {
    private WeakReference<AppCompatActivity> activity;

    @Override
    protected Boolean doInBackground(AppCompatActivity... activities) {
        this.activity = new WeakReference<>(activities[0]);
        ConnectivityManager cm = (ConnectivityManager) activity.get().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (cm != null)
            networkInfo = cm.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        ActionBar actionBar = activity.get().getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(b);
            if (b)
                actionBar.setIcon(R.drawable.iconwifi);
        }
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
