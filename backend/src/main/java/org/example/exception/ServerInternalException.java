package org.example.exception;

public class ServerInternalException extends RuntimeException {
    public ServerInternalException(String message) {
        super(message);
    }
    
}