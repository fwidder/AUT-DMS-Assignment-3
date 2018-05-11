package com.hotservice.sauron.utils;

import android.content.Context;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;

import com.hotservice.sauron.activities.StartActivity;
import com.hotservice.sauron.interfaces.ConnectivityInterface;
import com.hotservice.sauron.model.MessageWrapper;
import com.hotservice.sauron.model.Metadata;
import com.hotservice.sauron.model.NotSupportedException;
import com.hotservice.sauron.model.User;

import static android.nfc.NdefRecord.createMime;

public class NfcConnectivityManager implements ConnectivityInterface {
    private NfcAdapter nfcAdapter;


    public NfcConnectivityManager(Context context) {
        super();
        nfcAdapter = NfcAdapter.getDefaultAdapter(StartActivity.getAppContext());
    }

    @Override
    public boolean isAvailable(Context context) throws NotSupportedException {
        if (nfcAdapter == null)
            throw new NotSupportedException();
        return nfcAdapter.isEnabled();
    }

    @Override
    public Metadata getMetadata(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean connect(Context context, String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean disconnect(Context context) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendMessage(Context context, User receiver, final MessageWrapper message) throws NotSupportedException {
        nfcAdapter.setNdefPushMessageCallback(new NfcAdapter.CreateNdefMessageCallback() {
            @Override
            public NdefMessage createNdefMessage(NfcEvent event) {
                return new NdefMessage(
                        new NdefRecord[]{createMime(
                                "application/vnd.com.example.android.beam", message.toString().getBytes())
                                , NdefRecord.createApplicationRecord("com.example.android.beam")
                        });
            }
        }, null);

        throw new NotSupportedException();

    }

    @Override
    public boolean sendBroadcast(Context context, MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean pair(Context context, String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    /**
     * Starts a server waiting for clients
     *
     * @return success
     * @throws NotSupportedException when not supported by the manager
     */
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
