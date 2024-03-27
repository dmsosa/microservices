package com.duvi.services.stats.controller;


import com.duvi.services.stats.domain.exception.ApiError;
import com.duvi.services.stats.domain.exception.EntityExistsException;
import com.duvi.services.stats.domain.exception.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ApiError> errorHandler(Exception exception, WebRequest webRequest) {
        HttpHeaders headers = new HttpHeaders();
        if (exception instanceof EntityExistsException) {
            EntityExistsException uee = (EntityExistsException) exception;
            HttpStatus status = HttpStatus.CONFLICT;
            return ueeHandler(uee, headers, status);
        } else if ( exception instanceof EntityNotFoundException) {
            EntityNotFoundException unee = (EntityNotFoundException) exception;
            HttpStatus status = HttpStatus.NOT_FOUND;
            return aneeHandler(unee, headers, status);
        }
        else {
            HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
            ApiError body = new ApiError(exception);
            return internalHandler(body, headers, status);
        }
    }
    public ResponseEntity<ApiError> ueeHandler(EntityExistsException e, HttpHeaders headers, HttpStatus status) {
        ApiError body = new ApiError(e);
        return internalHandler(body, headers, status);
    }
    public ResponseEntity<ApiError> aneeHandler(EntityNotFoundException e, HttpHeaders headers, HttpStatus status) {
        ApiError body = new ApiError(e);
        return internalHandler(body, headers, status);
    }
    public ResponseEntity<ApiError> internalHandler(ApiError body, HttpHeaders headers, HttpStatus status) {
        return new ResponseEntity<>(body, headers, status);
    }
}
