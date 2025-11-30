package com.rmall.parking.exception;

/**
 * Custom checked exception for parking full scenarios.
 */
public class ParkingFullException extends Exception {
    public ParkingFullException(String message) {
        super(message);
    }
    
    public ParkingFullException(String message, Throwable cause) {
        super(message, cause);
    }
}