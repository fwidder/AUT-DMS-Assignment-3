package com.hotservice.sauron.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private static Group me;
    private List<User> users;

    private Group() {
        users = Collections.synchronizedList(new ArrayList<User>());
    }

    public synchronized Group getInstance() {
        if (me == null)
            me = new Group();
        else
            return me;
        return getInstance();
    }

    public synchronized List<User> getUserList() {
        return users;
    }

    public String userListToJson() {
        Gson gson = new Gson();

        return gson.toJson(getUserList());
    }

    public void importJsonToList(String json) {
        Gson gson = new Gson();
        ArrayList tmp = gson.fromJson(json, ArrayList.class);
        getUserList().clear();
        for (Object o : tmp) {
            User u = (User) o;
            getUserList().add(u);
        }
    }
}
