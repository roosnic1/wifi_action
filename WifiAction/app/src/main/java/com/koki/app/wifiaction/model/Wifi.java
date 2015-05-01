package com.koki.app.wifiaction.model;

import java.io.Serializable;

/**
 * Created by koki on 29/04/15.
 */
public class Wifi implements Serializable {
    private String ssid;
    private int networkId;

    public Wifi(String ssid, int networkId) {
        this.ssid = ssid;
        this.networkId = networkId;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public int getNetworkId() {
        return networkId;
    }

    public void setNetworkId(int networkId) {
        this.networkId = networkId;
    }
}
