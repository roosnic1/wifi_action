package com.koki.app.wifiaction.add;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.koki.app.wifiaction.R;
import com.koki.app.wifiaction.model.ActionType;
import com.koki.app.wifiaction.model.Wifi;
import com.koki.app.wifiaction.model.Action;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends Activity {

    private Spinner spWifis;
    private CheckBox cbOnConnect;
    private CheckBox cbOnLeave;
    private EditText etMessage;
    private EditText etTitle;
    private Button bnAdd;

    private ArrayList<Wifi> wifis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        //Get Widgets
        spWifis = (Spinner)findViewById(R.id.spWifis);
        cbOnConnect = (CheckBox)findViewById(R.id.cbOnConnect);
        cbOnLeave = (CheckBox)findViewById(R.id.cbOnLeave);
        etMessage = (EditText)findViewById(R.id.etMessage);
        etTitle = (EditText)findViewById(R.id.etTitle);
        bnAdd = (Button)findViewById(R.id.bnAdd);

        //Call setups

        if(savedInstanceState == null) {
            wifis = (ArrayList<Wifi>) getIntent().getExtras().getSerializable("WIFIS");
        } else {
            wifis = (ArrayList<Wifi>) savedInstanceState.getSerializable("WIFIS");
        }

        if(wifis != null) {
            setupSpWifis();
            setupButton();
        } else {
            //TODO: Check this Should never happen
            finish();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("WIFIS", wifis);
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


    private void setupSpWifis() {
        WifiAdapter wifiAdapter = new WifiAdapter(this,R.layout.spinneritem_wifi,wifis);
        spWifis.setAdapter(wifiAdapter);
    }

    private void setupButton() {
        bnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndReturnAction();
            }
        });
    }


    private void validateAndReturnAction() {
        //Validation
        if(!cbOnConnect.isChecked() && !cbOnLeave.isChecked()) {
            //TODO: Toast
            return;
        } else if(etTitle.getText().length() == 0) {
            //Todo: Toast
            return;
        } else if(etMessage.getText().length() == 0) {
            //Todo: Toast
            return;
        }

        Action a = new Action(etTitle.getText().toString(),((Wifi)spWifis.getSelectedItem()).getSsid(), ActionType.NOTIFICATION,cbOnConnect.isChecked(),cbOnLeave.isChecked());
        a.setStringParam1(etMessage.getText().toString());
        Intent i = new Intent();
        i.putExtra("ACTION",a);
        setResult(RESULT_OK, i);
        finish();
    }





    private class WifiAdapter extends ArrayAdapter<Wifi> {

        private ArrayList<Wifi> mWifi;
        private int mResourceId;

        public WifiAdapter(Context context, int resourceId, ArrayList<Wifi> objects) {
            super(context, resourceId, objects);
            mWifi = objects;
            mResourceId = resourceId;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResourceId,null);
            }

            //TextView tvWifiName = (TextView) convertView.findViewById(R.id.tvWifiName);
            ((TextView)convertView).setText("Wifi: " + mWifi.get(position).getSsid());

            return convertView;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            //return super.getDropDownView(position, convertView, parent);
            if(convertView == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResourceId,null);
            }

            //TextView tvWifiName = (TextView) convertView.findViewById(R.id.tvWifiName);
            ((TextView)convertView).setText(mWifi.get(position).getSsid());

            return convertView;
        }
    }
}
