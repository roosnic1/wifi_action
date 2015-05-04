package com.koki.app.wifiaction.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by koki on 04/05/15.
 */
public class SmsDeliveredReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch(getResultCode()) {
            case Activity.RESULT_OK:
                Log.i("SDR","SMS delivered");
                break;
            case Activity.RESULT_CANCELED:
                Log.i("SDR","SMS not delivered");
                break;
        }
    }
}
