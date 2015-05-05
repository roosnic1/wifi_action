package com.koki.app.wifiaction.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;

import com.koki.app.wifiaction.ActionService;

public class ConnectivityChangedReceiver extends BroadcastReceiver {

    private static final String TAG = "CCReceiver";
    private static final String CW = "CURRENTWIFI";
    private static final String CS = "CURRENTSTATE";

    //private static String mCurrentWifi = "";
    //private static String mCurrentState = "";

    public ConnectivityChangedReceiver() {


    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("com.koki.app.wifiaction",Context.MODE_PRIVATE);
        String currentWifi = sharedPreferences.getString(CW,"");
        String currentState = sharedPreferences.getString(CS,"");
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if(networkInfo != null) {
            if(networkInfo.getTypeName().equals(currentState) && networkInfo.getExtraInfo().equals(currentWifi) ) {
                //Log.i(TAG,"Already got it");
            } else if (networkInfo.getTypeName().equals("WIFI") && !networkInfo.getExtraInfo().equals(currentWifi)) {
                Log.i(TAG, "Changed to new WIFI: " + networkInfo.getExtraInfo());
                ActionService.startAction(context, networkInfo.getExtraInfo(), true);
                sharedPreferences.edit().putString(CW, networkInfo.getExtraInfo()).putString(CS,networkInfo.getTypeName()).commit();
                //mCurrentWifi = networkInfo.getExtraInfo();
                //mCurrentState = networkInfo.getTypeName();
            } else if(currentState.equals("WIFI") && !networkInfo.getTypeName().equals("WIFI")) {
                Log.i(TAG,"Left WIFI: " + currentWifi);
                ActionService.startAction(context, currentWifi, false);
                sharedPreferences.edit().putString(CW,networkInfo.getExtraInfo()).putString(CS,networkInfo.getTypeName()).commit();
                //mCurrentWifi = networkInfo.getExtraInfo();
                //mCurrentState = networkInfo.getTypeName();
            }
        }
    }

}
