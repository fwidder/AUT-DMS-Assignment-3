package com.hotservice.sauron.model.messages;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;

public class NfcMessage extends AbstractMessage implements NfcAdapter.CreateNdefMessageCallback {

    public NfcMessage(String message) {
        super(message);
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        return new NdefMessage(ndefRecord);
    }

    public String getMessage() {
        return message;
    }
}
