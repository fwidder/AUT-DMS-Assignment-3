package com.hotservice.sauron.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String ID, uuid, mobileNumber, name;
    private boolean smsState = false;

    /**
     * Create a new user
     */
    public User() {
        uuid = UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", uuid='" + uuid + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", name='" + name + '\'' +
                ", smsState=" + smsState +
                '}';
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
