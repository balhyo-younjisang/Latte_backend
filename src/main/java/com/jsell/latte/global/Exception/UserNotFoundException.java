package com.jsell.latte.global.Exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String m) {
        super(m);
    }
}
