package com.koki.app.wifiaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "CCReceiver";

    public ConnectivityChangedReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null) {
            Log.i(TAG,networkInfo.getDetailedState().toString());
            if(networkInfo.isConnected()) {
                Log.i(TAG, "Device is connected");
            } else {
                Log.i(TAG, "Device is not connected");
            }

        }
    }
}
