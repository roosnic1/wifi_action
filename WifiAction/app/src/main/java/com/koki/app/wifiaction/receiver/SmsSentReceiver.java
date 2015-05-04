package com.koki.app.wifiaction.receiver;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.util.Log;

/**
 * Created by koki on 04/05/15.
 */
public class SmsSentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch(getResultCode()) {
            case Activity.RESULT_OK:
                Log.i("SSR", "SMS Sent");
                break;
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                Log.i("SSR", "SMS generic failure");
                break;
            case SmsManager.RESULT_ERROR_NO_SERVICE:
                Log.i("SSR","SMS no service");
                break;
            case SmsManager.RESULT_ERROR_NULL_PDU:
                Log.i("SSR","SMS null PDU");
                break;
            case SmsManager.RESULT_ERROR_RADIO_OFF:
                Log.i("SSR","SMS radio off");
        }
    }
}
