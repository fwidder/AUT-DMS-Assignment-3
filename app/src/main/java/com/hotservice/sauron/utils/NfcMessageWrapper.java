package com.hotservice.sauron.utils;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;

import com.hotservice.sauron.model.messages.AbstractMessage;

public class NfcMessageWrapper implements NfcAdapter.CreateNdefMessageCallback {
    private final AbstractMessage message;

    public NfcMessageWrapper(AbstractMessage m) {
        this.message = m;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", new MessageHelper().toBytes(message));
        return new NdefMessage(ndefRecord);
    }

}
