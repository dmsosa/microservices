package com.duvi.services.account.domain.exception;

public class EntityExistsException extends Exception {
    public EntityExistsException(String message) {
        super(message);
    }
}
