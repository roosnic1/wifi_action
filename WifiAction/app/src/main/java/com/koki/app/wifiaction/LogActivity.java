package com.koki.app.wifiaction;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.koki.app.wifiaction.adapter.LogsRealmAdapter;
import com.koki.app.wifiaction.model.LogEntry;

import io.realm.Realm;
import io.realm.RealmResults;


public class LogActivity extends Activity {

    private ListView lvLogs;
    private LogsRealmAdapter mLogsRealmAdapter;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        realm = Realm.getInstance(this);

        lvLogs = (ListView) findViewById(R.id.lvLogs);
        setupLogsListView();

    }


    private void setupLogsListView() {
        try {
            RealmResults<LogEntry> result = realm.where(LogEntry.class).findAll();
            result.sort("date",false);
            mLogsRealmAdapter = new LogsRealmAdapter(this,result,true);
            lvLogs.setAdapter(mLogsRealmAdapter);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm != null) {
            realm.close();
        }
    }
}
