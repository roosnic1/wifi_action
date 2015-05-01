package com.koki.app.wifiaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.koki.app.wifiaction.model.Action;
import com.koki.app.wifiaction.model.Wifi;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements ContentHandler.IContentHandlerCallback{

    private static final String TAG = "MainActivity";

    private static final int NOTIFICATION_ACTIVITY = 1;



    private ContentHandler mContentHandler;
    private ArrayList<Action> mActionList;

    private ListView lvActions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnNotifiy = (Button) findViewById(R.id.btnNotify);
        lvActions = (ListView) findViewById(R.id.lvActions);
        btnNotifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotifiycation();
            }
        });
        if(savedInstanceState == null) {
            loadActionList();
        } else {
            mActionList = (ArrayList<Action>) savedInstanceState.getSerializable("ACTIONLIST");
        }

        setupActionList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ACTIONLIST", mActionList);
    }

    private void startNotifiycation() {
        Intent i = new Intent(this, ActionActivity.class);
        i.putExtra("WIFIS",getKnownWifi());
        startActivityForResult(i,NOTIFICATION_ACTIVITY);
        //ActionService.startActionNotification(this,"Hello :)");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case NOTIFICATION_ACTIVITY:
                if(resultCode == RESULT_OK) {
                    Log.i("MA", "Result OK");
                    Action a = (Action) data.getSerializableExtra("ACTION");
                    mActionList.add(a);
                    saveActionList(); //TODO Implement observer for save
                    setupActionList(); //TODO Implement observer for setupactionlist
                    Log.i("MA", "Action Title: " + a.getTitle());
                } else {
                    Log.i("MA","Result: " + resultCode);
                }
        }
    }

    private ArrayList<Wifi> getKnownWifi() {
        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> wifis = wifi.getConfiguredNetworks();
        ArrayList<Wifi> wifiList = new ArrayList<>();
        for(WifiConfiguration w : wifis) {
            Wifi wi = new Wifi(w.SSID,w.networkId);
            wifiList.add(wi);
        }
        return wifiList;
    }


    private void setupActionList() {
        if(mActionList == null) {
            return;
        }

        ArrayAdapter<Action> actionAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,mActionList);
        lvActions.setAdapter(actionAdapter);
    }

    private void loadActionList() {
        if(mContentHandler == null) {
            mContentHandler = new ContentHandler(this,this);
        }
        mContentHandler.startLoadingActionList();
    }


    private void saveActionList() {
        if(mActionList == null) {
            return;
        }

        if(mContentHandler == null) {
            mContentHandler  = new ContentHandler(this,this);
        }

        mContentHandler.startSavingActionList(mActionList);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContentLoaded(ArrayList<Action> actionList) {
        mActionList = actionList;
        Log.i("MA","Loaded Actionlist");
        setupActionList();

    }

    @Override
    public void onContentSaved() {
        //TODO: Show Notification
    }

    @Override
    public void onError(String errorMessage) {
        //TODO: Show Error if critical
        Log.i("MA","Error while loading ActionList");
        mActionList = new ArrayList<>();
    }
}
