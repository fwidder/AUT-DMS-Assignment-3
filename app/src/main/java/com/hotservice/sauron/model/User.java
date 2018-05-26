package com.hotservice.sauron.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String ID, uuid, mobileNumber;

    /**
     * Create a new user
     */
    public User() {
        uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "ID='" + ID + '\'' +
                ", uuid='" + uuid + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                '}';
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
}
