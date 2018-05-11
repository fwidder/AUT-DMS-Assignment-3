package com.hotservice.sauron.utils;

import com.hotservice.sauron.interfaces.ConnectivityInterface;
import com.hotservice.sauron.model.MessageWrapper;
import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.NotSupportedException;
import com.hotservice.sauron.model.User;

public class BluetoothConnectivityManager implements ConnectivityInterface {
    public BluetoothConnectivityManager() {
        super();
    }

    @Override
    public boolean isAvailable() throws NotSupportedException {
        throw new NotSupportedException();
    }


    @Override
    public Metadata getMetadata() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean connect(String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean disconnect() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendMessage(User receiver, MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendBroadcast(MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean pair(String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean startServer() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean stopServer() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isConnected() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isServer() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isClient() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public MessageWrapper receive() {
        return null;
    }
}
