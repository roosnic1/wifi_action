package com.koki.app.wifiaction;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import com.koki.app.wifiaction.model.Action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class ActionService extends IntentService {
    private static final String TAG = "ActionService";


    private static final String ACTION_SMS = "com.koki.app.wifiaction.action.SMS";
    private static final String ACTION_BLUETOOTH = "com.koki.app.wifiaction.action.BLUETOOTH";
    private static final String ACTION_GPS = "com.koki.app.wifiaction.action.GPS";
    private static final String ACTION_NOTIFICATION = "com.koki.app.wifiaction.action.NOTIFICATION";

    private static final String EXTRA_WIFI = "com.koki.app.wifiaction.extra.PARAM1";
    private static final String EXTRA_ISCON = "com.koki.app.wifiaction.extra.PARAM2";

    private static final int NOTIFICATION_ID = 42;

    public static void startAction(Context context, String wifi, boolean isConnected) {
        Intent intent = new Intent(context, ActionService.class);
        //intent.setAction(ACTION_SMS);
        intent.putExtra(EXTRA_WIFI, wifi);
        intent.putExtra(EXTRA_ISCON, isConnected);
        context.startService(intent);
    }


    public ActionService() {
        super("ActionService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String wifi = intent.getStringExtra(EXTRA_WIFI);
            boolean isCon = intent.getBooleanExtra(EXTRA_ISCON,true);
            Log.i("AS","onHandleIntent with Wifi: " +  wifi);
            ArrayList<Action> aList = loadFile();
            for(int i=0;i<aList.size();i++) {
                Action a = aList.get(i);
                if(a.getSsid().equals(wifi) && (a.isOnConnect() == isCon || a.isOnLeave() != isCon)) {
                    switch(a.getActionType()) {
                        case BLUETOOTH:
                            handleActionBluetooth(a.isBooleanParam1());
                            break;
                        case GPS:
                            handleActionGPS(a.isBooleanParam1());
                            break;
                        case NOTIFICATION:
                            handleActionNotification(a.getStringParam1());
                            break;
                        case SMS:
                            handleActionSMS(a.getStringParam1(),a.getStringParam2());
                            break;
                    }
                }
            }
        }
    }


    private ArrayList<Action> loadFile() {
        ArrayList<Action> actionList = null;
        try {
            FileInputStream fis = this.openFileInput(ContentHandler.FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            actionList = (ArrayList<Action>) ois.readObject();
            ois.close();
        } catch(FileNotFoundException e) {
            //e.printStackTrace();
            Log.i(TAG,"File not Found... (Not critical)");
            actionList = new ArrayList<>();
        } catch(IOException | ClassNotFoundException e) {
            e.printStackTrace();
            //TODO: Implement proper Error handling
            Log.e(TAG,"Error while loading File: " + ContentHandler.FILE);
        }

        return actionList;
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
