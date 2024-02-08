package com.jsell.latte.global.Exception;

public class JwtExpiredException extends RuntimeException{
    public JwtExpiredException(String m) {
        super(m);
    }
}
