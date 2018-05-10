package com.hotservice.sauron.utils;

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


    public NfcConnectivityManager() {
        super();
        nfcAdapter = NfcAdapter.getDefaultAdapter(StartActivity.getAppContext());
    }

    @Override
    public boolean isAvailable() throws NotSupportedException {
        if (nfcAdapter == null)
            throw new NotSupportedException();
        return nfcAdapter.isEnabled();
    }

    @Override
    public Metadata getMetadata() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean connect(String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean disconnect() throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    public boolean sendMessage(User receiver, final MessageWrapper message) throws NotSupportedException {
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
    public boolean sendBroadcast(MessageWrapper message) throws NotSupportedException {
        throw new NotSupportedException();
    }

    @Override
    @Deprecated
    public boolean pair(String ID) throws NotSupportedException {
        throw new NotSupportedException();
    }

    /**
     * Starts a server waiting for clients
     *
     * @return success
     * @throws NotSupportedException
     */
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
