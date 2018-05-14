package com.hotservice.sauron.model.messages;

public abstract class AbstractMessage {
    protected final String message;

    public AbstractMessage(String msg) {
        this.message = msg;
    }
}
