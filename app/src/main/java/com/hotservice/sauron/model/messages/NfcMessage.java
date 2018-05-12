package com.hotservice.sauron.model.messages;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;

public class NfcMessage implements NfcAdapter.CreateNdefMessageCallback {
    private final String message;

    public NfcMessage(String message) {
        this.message = message;
    }

    @Override
    public NdefMessage createNdefMessage(NfcEvent event) {
        NdefRecord ndefRecord = NdefRecord.createMime("text/plain", message.getBytes());
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);
        return ndefMessage;
    }

    public String getMessage() {
        return message;
    }
}
