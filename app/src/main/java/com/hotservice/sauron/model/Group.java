package com.hotservice.sauron.model;

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
}
