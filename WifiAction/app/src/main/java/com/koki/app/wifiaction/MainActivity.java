package com.koki.app.wifiaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;

import com.koki.app.wifiaction.adapter.ActionArrayAdapter;
import com.koki.app.wifiaction.model.Action;
import com.koki.app.wifiaction.model.Wifi;
import com.software.shell.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements ContentHandler.IContentHandlerCallback{

    private static final String TAG = "MainActivity";

    public static final int ACTION_SMS = 1;
    public static final int ACTION_BLUETOOTH = 2;
    public static final int ACTION_GPS = 3;
    public static final int ACTION_NOTIFICATION = 4;



    private ContentHandler mContentHandler;
    private ArrayList<Action> mActionList;

    private ListView lvActions;
    private ActionArrayAdapter actionAdapter;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);

        lvActions = (ListView) findViewById(R.id.lvActions);
        ActionButton btnNotifiy = (ActionButton) findViewById(R.id.btnAddAction);
        btnNotifiy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNotifiycation();
            }
        });
        setupActionList();
        if(savedInstanceState == null) {
            loadActionList();
        } else {
            mActionList.addAll((ArrayList<Action>) savedInstanceState.getSerializable("ACTIONLIST"));
            actionAdapter.notifyDataSetChanged();
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("ACTIONLIST", mActionList);
    }


    private void startNotifiycation() {
        Intent i = new Intent(this, ActionActivity.class);
        i.putExtra("WIFIS", getKnownWifi());
        startActivityForResult(i, ACTION_NOTIFICATION);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case ACTION_NOTIFICATION:
                if(resultCode == RESULT_OK) {
                    Action a = (Action) data.getSerializableExtra("ACTION");
                    mActionList.add(a);
                    actionAdapter.notifyDataSetChanged();
                    saveActionList();
                } else {
                    Log.i("MA","Result: " + resultCode);
                }
        }
    }

    private ArrayList<Wifi> getKnownWifi() {
        //TODO: Check if wifi is enabled otherwise notify and kill APP -> Get this into the Doc
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
        mActionList = new ArrayList<>();
        actionAdapter = new ActionArrayAdapter(this,R.layout.listitem_action,mActionList);
        lvActions.setAdapter(actionAdapter);
        lvActions.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lvActions.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            private int count;

            @Override
            public void onItemCheckedStateChanged(ActionMode actionMode, int i, long l, boolean b) {
                if(b) {
                    count += 1;
                } else {
                    count -= 1;
                }
                actionMode.setTitle(getResources().getQuantityString(R.plurals.actions,count,count));

            }

            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                MenuInflater inflater = actionMode.getMenuInflater();
                inflater.inflate(R.menu.menu_con_listview,menu);
                count = 0;
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.action_delete:
                        SparseBooleanArray positions = lvActions.getCheckedItemPositions();
                        for(int i=lvActions.getCount()-1;i>=0;i--) {
                            if(positions.get(i)) {
                                mActionList.remove(i);
                            }
                        }
                        actionAdapter.notifyDataSetChanged();
                        saveActionList();
                        actionMode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {

            }
        });
    }

    private void loadActionList() {
        if(mContentHandler == null) {
            mContentHandler = new ContentHandler(this,this);
        }
        mContentHandler.startLoadingActionList();
    }


    private void saveActionList() {
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
        if (id == R.id.action_logs) {
            Intent i = new Intent(mContext,LogActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onContentLoaded(ArrayList<Action> actionList) {
        mActionList.addAll(actionList);
        actionAdapter.notifyDataSetChanged();
        Log.i("MA", "Loaded Actionlist");
    }

    @Override
    public void onContentSaved() {
        //TODO: Show Notification
    }

    @Override
    public void onError(String errorMessage) {
        //TODO: Show Error if critical
        Log.i("MA","Error while loading ActionList");
    }



}
