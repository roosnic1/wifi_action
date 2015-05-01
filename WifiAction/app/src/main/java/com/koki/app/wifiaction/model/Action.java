package com.koki.app.wifiaction.model;

import java.io.Serializable;

/**
 * Created by koki on 25/03/15.
 */
public class Action implements Serializable {

    private String title;
    private String ssid;
    private ActionType actionType;
    private boolean onConnect;
    private boolean onLeave;

    private String stringParam1;
    private String stringParam2;
    private boolean booleanParam1;

    public Action(String title, String ssid, ActionType actionType, boolean onConnect, boolean onLeave) {
        this.title = title;
        this.ssid = ssid;
        this.actionType = actionType;
        this.onConnect = onConnect;
        this.onLeave = onLeave;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public boolean isOnConnect() {
        return onConnect;
    }

    public void setOnConnect(boolean onConnect) {
        this.onConnect = onConnect;
    }

    public boolean isOnLeave() {
        return onLeave;
    }

    public void setOnLeave(boolean onLeave) {
        this.onLeave = onLeave;
    }

    public String getStringParam1() {
        return stringParam1;
    }

    public void setStringParam1(String stringParam1) {
        this.stringParam1 = stringParam1;
    }

    public String getStringParam2() {
        return stringParam2;
    }

    public void setStringParam2(String stringParam2) {
        this.stringParam2 = stringParam2;
    }

    public boolean isBooleanParam1() {
        return booleanParam1;
    }

    public void setBooleanParam1(boolean booleanParam1) {
        this.booleanParam1 = booleanParam1;
    }

    @Override
    public String toString() {
        String when = "";
        if(isOnConnect()) {
            when = "OnConnect ";
        }
        if(isOnLeave()) {
            when += "OnLeave";
        }
        return  "Title: " + title + "\n" +
                "SSID: " + ssid + "\n" +
                "When: " + when;

    }
}
