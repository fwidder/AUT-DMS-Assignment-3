package com.hotservice.sauron.model.messages;

public class NFCMessage extends AbstractMessage {
    private String mac;
    private String name;

    public String getMac() {
        return mac;
    }

    public NFCMessage setMac(String mac) {
        this.mac = mac;
        return this;
    }

    public String getName() {
        return name;
    }

    public NFCMessage setName(String name) {
        this.name = name;
        return this;
    }
}
