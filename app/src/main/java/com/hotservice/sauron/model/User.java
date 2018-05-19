package com.hotservice.sauron.model;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private String ID, uuid;

    /**
     * Create a new user
     */
    public User() {
        uuid = UUID.randomUUID().toString();
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String UUID) {
        this.uuid = UUID;
    }
}
