package com.jsell.latte.global.Exception;

import java.net.SocketException;

public class SendMessageException extends SocketException {
    String m;

    public SendMessageException(String m) {
        super(m);
    }
}
