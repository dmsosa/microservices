package com.duvi.authservice.model.exception;

public class UserNotExistsException extends Exception {
    public UserNotExistsException(String message) {
        super(message);
    }
}