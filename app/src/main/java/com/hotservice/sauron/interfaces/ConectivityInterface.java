package com.hotservice.sauron.interfaces;

import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.User;

public interface ConectivityInterface {
    Metadata getMetadata();

    boolean connect(String ID);

    boolean disconnect();

    boolean sendMessage(User reciever, String message);

    boolean sendBroadcast(String message);

    boolean pair(String ID);

    boolean startServer();

    boolean stopServer();

    boolean isConnected();

    boolean isServer();

    boolean isClient();
}
