package com.koki.app.wifiaction.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.koki.app.wifiaction.R;
import com.koki.app.wifiaction.model.LogEntry;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by koki on 08/05/15.
 */
public class LogsRealmAdapter extends RealmBaseAdapter<LogEntry> implements ListAdapter {

    private static class ViewHolder {
        TextView logDate;
        TextView actionTitle;
        TextView actionWifi;
    }

    public LogsRealmAdapter(Context context, RealmResults<LogEntry> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(R.layout.listitem_logentry,viewGroup,false);
            holder = new ViewHolder();
            holder.logDate = (TextView) convertView.findViewById(R.id.tvLogDate);
            holder.actionTitle = (TextView) convertView.findViewById(R.id.tvActionTitle);
            holder.actionWifi = (TextView) convertView.findViewById(R.id.tvActionWifi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        LogEntry logEntry = realmResults.get(i);
        holder.logDate.setText(logEntry.getDate().toString());
        holder.actionTitle.setText(logEntry.getActionName());
        holder.actionWifi.setText(logEntry.getWifiSsid() + " Connected: " + logEntry.isOnConnect());

        return convertView;
    }
}
