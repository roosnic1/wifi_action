package com.koki.app.wifiaction.model;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by koki on 08/05/15.
 */
public class LogEntry extends RealmObject {

    private Date date;
    private String actionName;
    private String wifiSsid;
    private boolean onConnect;

    public LogEntry() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getWifiSsid() {
        return wifiSsid;
    }

    public void setWifiSsid(String wifiSsid) {
        this.wifiSsid = wifiSsid;
    }

    public boolean isOnConnect() {
        return onConnect;
    }

    public void setOnConnect(boolean onConnect) {
        this.onConnect = onConnect;
    }
}
