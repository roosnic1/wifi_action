package com.koki.app.wifiaction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "CCReceiver";

    private static String mCurrentWifi = "";
    private static String mCurrentState = "";

    public ConnectivityChangedReceiver() {


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null) {
            if(networkInfo.getTypeName().equals(mCurrentState) && networkInfo.getExtraInfo().equals(mCurrentWifi) ) {
                //Log.i(TAG,"Already got it");
            } else if (networkInfo.getTypeName().equals("WIFI") && !networkInfo.getExtraInfo().equals(mCurrentWifi)) {
                Log.i(TAG,"Changed to new WIFI: " + networkInfo.getExtraInfo());
                ActionService.startAction(context,networkInfo.getExtraInfo(),true);
                mCurrentWifi = networkInfo.getExtraInfo();
                mCurrentState = networkInfo.getTypeName();
            } else if(mCurrentState.equals("WIFI") && !networkInfo.getTypeName().equals("WIFI")) {
                Log.i(TAG,"Left WIFI: " + mCurrentWifi);
                ActionService.startAction(context, mCurrentWifi, false);
                mCurrentWifi = networkInfo.getExtraInfo();
                mCurrentState = networkInfo.getTypeName();
            }
        }
    }

}
