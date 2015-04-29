package com.koki.app.wifiaction.add;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.koki.app.wifiaction.R;
import com.koki.app.wifiaction.model.Wifi;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends Activity {

    private Spinner spWifis;
    private CheckBox cbOnConnect;
    private CheckBox cbOnLeave;
    private EditText etMessage;
    private Button bnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Get Widgets
        spWifis = (Spinner)findViewById(R.id.spWifis);
        cbOnConnect = (CheckBox)findViewById(R.id.cbOnConnect);
        cbOnLeave = (CheckBox)findViewById(R.id.cbOnLeave);
        etMessage = (EditText)findViewById(R.id.etMessage);
        bnAdd = (Button)findViewById(R.id.bnAdd);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notification, menu);
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


    private void setupSpWifis(ArrayList<Wifi> wifis) {

    }





    private class WifiAdapter extends ArrayAdapter<Wifi> {
        

        public WifiAdapter(Context context, int resource, int textViewResourceId, List<Wifi> objects) {
            super(context, resource, textViewResourceId, objects);
        }
    }
}
