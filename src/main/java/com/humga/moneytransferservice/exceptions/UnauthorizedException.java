package com.humga.moneytransferservice.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg) { super(msg); }
}
