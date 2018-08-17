package com.example.proshine001.webapplication.even;

/**
 * Created by proshine001 on 2018-08-17.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
