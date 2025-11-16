package com.dahohelping.dahohelping_springboot.exception;

public class InvalidScoreException extends RuntimeException {
    public InvalidScoreException(String message) {
        super(message);
    }
}
