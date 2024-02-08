package com.jsell.latte.global.Exception;

public class PasswordNotMatchException extends RuntimeException {
    public PasswordNotMatchException(String m) {
        super(m);
    }
}
