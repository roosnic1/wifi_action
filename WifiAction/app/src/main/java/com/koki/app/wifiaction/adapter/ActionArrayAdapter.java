package com.koki.app.wifiaction.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koki.app.wifiaction.R;
import com.koki.app.wifiaction.model.Action;

import java.util.ArrayList;

/**
 * Created by koki on 05/05/15.
 */
public class ActionArrayAdapter extends ArrayAdapter<Action> {

    //private ArrayList<Action> mActions;
    //private int mResource;
    private LayoutInflater mInflater;
    private static class ViewHolder {
        ImageView actionImage;
        TextView actionTitle;
        TextView actionWifi;
    }

    public ActionArrayAdapter(Context context, int resource, ArrayList<Action> objects) {
        super(context, resource, objects);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ArrayList<Action> getItems() {
        ArrayList<Action> actionList = new ArrayList<>();
        for(int i=0;i<getCount();i++) {
            actionList.add(getItem(i));
        }
        return actionList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_action,parent,false);
            holder = new ViewHolder();
            holder.actionImage = (ImageView) convertView.findViewById(R.id.ivAction);
            holder.actionTitle = (TextView) convertView.findViewById(R.id.tvActionTitle);
            holder.actionWifi = (TextView) convertView.findViewById(R.id.tvActionWifi);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Action action = getItem(position);

        holder.actionTitle.setText(action.getTitle());
        holder.actionWifi.setText(action.getSsid());

        switch(action.getActionType()) {
            case SMS:
                holder.actionImage.setImageResource(R.drawable.action_message);
                break;
            case BLUETOOTH:
                holder.actionImage.setImageResource(R.drawable.action_bluetooth);
                break;
            case GPS:
                holder.actionImage.setImageResource(R.drawable.action_gps);
                break;
            case NOTIFICATION:
                holder.actionImage.setImageResource(R.drawable.action_notification);
                break;
        }
        return convertView;
    }
}
