package com.duvi.services.stats.domain.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiError {
    private Class<? extends Exception> exception;
    private String message;


    public ApiError(Exception exception) {
        this.exception = exception.getClass();
        this.message = exception.getMessage() + " caused by " + exception.getCause() + " StackTrace: " + exception.getStackTrace() + " localizedMessage: " + exception.getLocalizedMessage();
    };
    public String toString() {
        return "{ \"error\" : \"" + getException().getName()+ "\"" +
                "\n\"message\": \"" + getMessage() + "\" } ";
    }
}
