// ParkingSlot.java
package com.rmall.parking.model;

/**
 * The ParkingSlot class represents a single parking slot in the system.
 * It will manage the state of the parking slot, whether it is occupied
 * or not and provides thread-safe operations on these states.
 */
public class ParkingSlot {
    
    // The 'volatile' keyword ensures that changes to this variable are
    // immediately visible to other threads. This is important in a 
    // multithreaded environment where one thread may be modifying the 
    // variable while another thread reads it.
    private volatile boolean occupied;

    // The constructor initializes the parking slot as not occupied.
    public ParkingSlot() {
        this.occupied = false;
    }

    /**
     * Marks the parking slot as occupied. This method is synchronized
     * to ensure that only one thread can execute it at a time. This 
     * prevents race conditions where multiple threads might try 
     * to occupy the slot simultaneously.
     */
    public synchronized void occupy() {
        if (!occupied) {
            occupied = true;
        } else {
            throw new IllegalStateException("Parking slot is already occupied.");
        }
    }

    /**
     * Marks the parking slot as vacant. This method is synchronized
     * to prevent multiple threads from changing the occupied status
     * simultaneously.
     */
    public synchronized void vacate() {
        if (occupied) {
            occupied = false;
        } else {
            throw new IllegalStateException("Parking slot is already vacant.");
        }
    }

    /**
     * Returns the occupancy status of the parking slot.
     * 
     * @return true if the slot is occupied, false otherwise.
     */
    public boolean isOccupied() {
        return occupied;
    }
}