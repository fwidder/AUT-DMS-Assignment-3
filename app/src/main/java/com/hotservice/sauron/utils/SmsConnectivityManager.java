package com.hotservice.sauron.utils;

import android.content.Context;

import com.hotservice.sauron.interfaces.ConnectivityInterface;
import com.hotservice.sauron.model.MessageWrapper;
import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.NotSupportedException;
import com.hotservice.sauron.model.User;

public class SmsConnectivityManager implements ConnectivityInterface {
    public SmsConnectivityManager() {
        super();
    }

    @Override
    public boolean isAvailable(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }


    @Override
    public Metadata getMetadata(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean connect(Context context, String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean disconnect(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendMessage(Context context, User receiver, MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendBroadcast(Context context, MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean pair(Context context, String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean startServer(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean stopServer(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isConnected(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isServer(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean isClient(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public MessageWrapper receive(Context context) {
        return null;
    }
}
