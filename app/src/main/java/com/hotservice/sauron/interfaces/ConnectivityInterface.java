package com.hotservice.sauron.interfaces;

import android.content.Context;

import com.hotservice.sauron.model.MessageWrapper;
import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.NotSupportedException;
import com.hotservice.sauron.model.User;

public interface ConnectivityInterface {

    boolean isAvailable(Context context) throws NotSupportedException;

    Metadata getMetadata(Context context) throws NotSupportedException;

    boolean connect(Context context, String ID) throws NotSupportedException;

    boolean disconnect(Context context) throws NotSupportedException;

    boolean sendMessage(Context context, User receiver, MessageWrapper message) throws NotSupportedException;

    boolean sendBroadcast(Context context, MessageWrapper message) throws NotSupportedException;

    boolean pair(Context context, String ID) throws NotSupportedException;

    boolean startServer(Context context) throws NotSupportedException;

    boolean stopServer(Context context) throws NotSupportedException;

    boolean isConnected(Context context) throws NotSupportedException;

    boolean isServer(Context context) throws NotSupportedException;

    boolean isClient(Context context) throws NotSupportedException;

    MessageWrapper receive(Context context) throws NotSupportedException;
}
