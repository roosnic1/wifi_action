package com.koki.app.wifiaction;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class ActionService extends IntentService {
    private static final String ACTION_SMS = "com.koki.app.wifiaction.action.SMS";
    private static final String ACTION_BLUETOOTH = "com.koki.app.wifiaction.action.BLUETOOTH";
    private static final String ACTION_GPS = "com.koki.app.wifiaction.action.GPS";
    private static final String ACTION_NOTIFICATION = "com.koki.app.wifiaction.action.NOTIFICATION";

    private static final String EXTRA_PARAM1 = "com.koki.app.wifiaction.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.koki.app.wifiaction.extra.PARAM2";

    private static final int NOTIFICATION_ID = 42;

    public static void startActionSMS(Context context, String number, String message) {
        Intent intent = new Intent(context, ActionService.class);
        intent.setAction(ACTION_SMS);
        intent.putExtra(EXTRA_PARAM1, number);
        intent.putExtra(EXTRA_PARAM2, message);
        context.startService(intent);
    }

    public static void startActionBluetooth(Context context, boolean turnOn) {
        Intent intent = new Intent(context, ActionService.class);
        intent.setAction(ACTION_BLUETOOTH);
        intent.putExtra(EXTRA_PARAM1,turnOn);
        context.startService(intent);
    }

    public static void startActionGPS(Context context, boolean turnOn) {
        Intent intent = new Intent(context, ActionService.class);
        intent.setAction(ACTION_GPS);
        intent.putExtra(EXTRA_PARAM1,turnOn);
        context.startService(intent);
    }

    public static void startActionNotification(Context context, String message) {
        Intent intent = new Intent(context, ActionService.class);
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(EXTRA_PARAM1,message);
        context.startService(intent);
    }

    public ActionService() {
        super("ActionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SMS.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionSMS(param1, param2);
            } else if (ACTION_BLUETOOTH.equals(action)) {
                final boolean param1 = intent.getBooleanExtra(EXTRA_PARAM1, false);
                handleActionBluetooth(param1);
            } else if (ACTION_GPS.equals(action)) {
                final boolean param1 = intent.getBooleanExtra(EXTRA_PARAM1,false);
                handleActionGPS(param1);
            } else if (ACTION_NOTIFICATION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                handleActionNotification(param1);
            }
        }
    }


    private void handleActionSMS(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }


    private void handleActionBluetooth(boolean param1) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionGPS(boolean param1) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void handleActionNotification(String param1) {
        Notification.Builder mBuilder = new Notification.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher).setContentTitle("Wifi Action").setContentText(param1);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID,mBuilder.build());
    }
}
