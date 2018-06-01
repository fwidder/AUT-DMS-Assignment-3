package com.hotservice.sauron.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String ID;
    private String uuid;
    private String mobileNumber;
    private String name;
    private String BTMac;
    private boolean smsState = false;

    /**
     * Create a new user
     */
    public User() {
        uuid = UUID.randomUUID().toString();
    }

    public String getBTMac() {
        return BTMac;
    }

    public void setBTMac(String BTMac) {
        this.BTMac = BTMac;
    }

    public boolean isSmsState() {
        return smsState;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSmsState() {
        return smsState;
    }

    public void setSmsState(boolean smsState) {
        this.smsState = smsState;
    }
}
