package com.example.springtesttaskv1.exception;

public class ClientApplicationException extends RuntimeException{
    public ClientApplicationException(String message) {
        super(message);
    }
}
