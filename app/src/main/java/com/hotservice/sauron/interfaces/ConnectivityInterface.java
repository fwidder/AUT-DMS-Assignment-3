package com.hotservice.sauron.interfaces;

import com.hotservice.sauron.model.MessageWrapper;
import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.NotSupportedException;
import com.hotservice.sauron.model.User;

public interface ConnectivityInterface {

    boolean isAvailable() throws NotSupportedException;

    Metadata getMetadata() throws NotSupportedException;

    boolean connect(String ID) throws NotSupportedException;

    boolean disconnect() throws NotSupportedException;

    boolean sendMessage(User receiver, MessageWrapper message) throws NotSupportedException;

    boolean sendBroadcast(MessageWrapper message) throws NotSupportedException;

    boolean pair(String ID) throws NotSupportedException;

    boolean startServer() throws NotSupportedException;

    boolean stopServer() throws NotSupportedException;

    boolean isConnected() throws NotSupportedException;

    boolean isServer() throws NotSupportedException;

    boolean isClient() throws NotSupportedException;

    MessageWrapper receive() throws NotSupportedException;
}
