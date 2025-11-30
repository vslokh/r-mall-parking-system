package com.rmall.parking.exception;

public class InvalidTokenException extends Exception {
    private final int invalidToken;
    
    public InvalidTokenException(int token) {
        super("Invalid parking token: " + token);
        this.invalidToken = token;
    }
    
    public InvalidTokenException(String message, int token) {
        super(message);
        this.invalidToken = token;
    }
    
    public int getInvalidToken() {
        return invalidToken;
    }
}