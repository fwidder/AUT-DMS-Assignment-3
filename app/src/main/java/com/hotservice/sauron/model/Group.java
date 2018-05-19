package com.hotservice.sauron.model;

import com.hotservice.sauron.utils.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Group {
    private static Group me;
    private List<User> users;

    private Group() {
        users = Collections.synchronizedList(new ArrayList<User>());
    }

    /**
     * Instance of the class (Singleton)
     *
     * @return Group instance
     */
    public static synchronized Group getInstance() {
        if (me == null)
            me = new Group();
        else
            return me;
        return getInstance();
    }

    /**
     * get user list
     *
     * @return list of all users
     */
    public synchronized List<User> getUserList() {
        return users;
    }

    /**
     * get current user
     *
     * @return own user object
     * @throws Exception when current user can not be found
     */
    public synchronized User getMe() throws Exception {
        for (User u : getUserList()) {
            if (u.getID().equals(Config.USER_ID))
                return u;
        }
        throw new Exception("Can't find User. Start PANIC-Protocol...");
    }
}
