package com.koki.app.wifiaction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.koki.app.wifiaction.model.ActionType;
import com.koki.app.wifiaction.model.Wifi;
import com.koki.app.wifiaction.model.Action;

import java.util.ArrayList;

public class ActionActivity extends Activity {

    private final static int REQUEST_CONTACTPICKER = 42;

    private Spinner spActionType;
    private Spinner spWifis;
    private CheckBox cbOnConnect;
    private CheckBox cbOnLeave;
    private EditText etMessage1;
    private EditText etMessage2;
    private EditText etMessage3;
    private EditText etTitle;
    private Switch swBoolean;
    private Button bnAdd;
    private ImageButton bnContact;
    private LinearLayout llNumberInput;

    private ArrayList<Wifi> wifis;
    private ArrayAdapter actionAdapter;
    private int mActionType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        //Get Widgets
        spActionType = (Spinner)findViewById(R.id.spActionType);
        spWifis = (Spinner)findViewById(R.id.spWifis);
        cbOnConnect = (CheckBox)findViewById(R.id.cbOnConnect);
        cbOnLeave = (CheckBox)findViewById(R.id.cbOnLeave);
        etMessage1 = (EditText)findViewById(R.id.etMessage1);
        etMessage2 = (EditText)findViewById(R.id.etMessage2);
        etMessage3 = (EditText)findViewById(R.id.etMessage3);
        etTitle = (EditText)findViewById(R.id.etTitle);
        swBoolean = (Switch)findViewById(R.id.swBoolean);
        bnAdd = (Button)findViewById(R.id.bnAdd);
        bnContact = (ImageButton)findViewById(R.id.bnContact);
        llNumberInput = (LinearLayout)findViewById(R.id.llNumberInput);

        //Call setups

        if(savedInstanceState == null) {
            wifis = (ArrayList<Wifi>) getIntent().getExtras().getSerializable("WIFIS");
            mActionType = 0;
        } else {
            wifis = (ArrayList<Wifi>) savedInstanceState.getSerializable("WIFIS");
            mActionType = savedInstanceState.getInt("ACTIONTYPE");
        }

        if(wifis != null) {
            setupSpActionType();
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
        outState.putInt("ACTIONTYPE", mActionType);
    }

    private  void setupSpActionType() {
        actionAdapter = ArrayAdapter.createFromResource(this,R.array.action_type,android.R.layout.simple_spinner_dropdown_item);
        spActionType.setAdapter(actionAdapter);
        spActionType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mActionType = position;
                switch (position) {
                    case 0:
                        etMessage1.setVisibility(View.INVISIBLE);
                        etMessage2.setVisibility(View.VISIBLE);
                        swBoolean.setVisibility(View.GONE);
                        llNumberInput.setVisibility(View.VISIBLE);
                        etMessage2.setHint(R.string.act_et_sms_hint);
                        break;
                    case 1:
                        etMessage1.setVisibility(View.GONE);
                        etMessage2.setVisibility(View.GONE);
                        swBoolean.setVisibility(View.VISIBLE);
                        llNumberInput.setVisibility(View.GONE);
                        break;
                    case 2:
                        etMessage1.setVisibility(View.GONE);
                        etMessage2.setVisibility(View.GONE);
                        swBoolean.setVisibility(View.VISIBLE);
                        llNumberInput.setVisibility(View.GONE);
                        break;
                    case 3:
                        etMessage1.setVisibility(View.VISIBLE);
                        etMessage2.setVisibility(View.GONE);
                        swBoolean.setVisibility(View.GONE);
                        llNumberInput.setVisibility(View.GONE);
                        etMessage1.setHint(R.string.act_et_message_hint);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
        bnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
                startActivityForResult(i,REQUEST_CONTACTPICKER);
            }
        });

    }


    private void validateAndReturnAction() {
        //Validation
        if(!cbOnConnect.isChecked() && !cbOnLeave.isChecked()) {
            //TODO: Toast
            return;
        } else if(etTitle.getText().length() == 0) {
            //TODO: Toast
            return;
        } else if(mActionType == 3 && etMessage1.getText().length() == 0) {
            //TODO: Toast
            return;
        } else if(mActionType == 0 && (etMessage3.getText().length() == 0 || etMessage2.getText().length() ==0)) {
            //TODO: Toast
            return;
        }
        ActionType at = ActionType.SMS;
        switch(mActionType) {
            case 1:
                at = ActionType.BLUETOOTH;
                break;
            case 2:
                at = ActionType.GPS;
                break;
            case 3:
                at = ActionType.NOTIFICATION;
                break;

        }

        Action a = new Action(etTitle.getText().toString(),((Wifi)spWifis.getSelectedItem()).getSsid(), at,cbOnConnect.isChecked(),cbOnLeave.isChecked());
        if(mActionType == 0) {
            a.setStringParam1(etMessage3.getText().toString());
        } else {
            a.setStringParam1(etMessage1.getText().toString());
        }
        a.setStringParam2(etMessage2.getText().toString());
        a.setBooleanParam1(swBoolean.isChecked());
        Intent i = new Intent();
        i.putExtra("ACTION",a);
        setResult(RESULT_OK, i);
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_CONTACTPICKER:
                if(resultCode == RESULT_OK) {
                    Uri contentUri = data.getData();
                    Cursor cursor = getContentResolver().query(contentUri, null, null, null, null);
                    cursor.moveToFirst();
                    String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    if(number.length() > 0) {
                        etMessage3.setText(number);
                    }
                }
        }
    }

    /* ArrayAdapter for Wifi spinner */
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
