package com.hotservice.sauron.model.messages;

import com.hotservice.sauron.model.User;

import java.util.Arrays;
import java.util.List;

public class BluetoothMessage extends AbstractMessage {
    private String serverUUID;
    private List<User> userList;

    public BluetoothMessage(String serverUUID, List<User> userList) {
        this.serverUUID = serverUUID;
        this.userList = userList;
    }

    @Override
    public String toString() {
        return "BluetoothMessage{" +
                "serverUUID='" + serverUUID + '\'' +
                ", userList=" + Arrays.toString(userList.toArray(new User[0])) +
                '}';
    }

    public String getServerUUID() {
        return serverUUID;
    }

    public void setServerUUID(String serverUUID) {
        this.serverUUID = serverUUID;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
