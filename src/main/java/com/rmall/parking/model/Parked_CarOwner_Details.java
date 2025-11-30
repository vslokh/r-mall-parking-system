package com.rmall.parking.model;

/**
 * JAVA 23 RECORD - Parked Car Owner Details
 * A record is an immutable data carrier class introduced in Java 14 and finalized in Java 16.
 * Records automatically generate constructor, getters, equals(), hashCode(), and toString().
 */
public record Parked_CarOwner_Details(
    String ownerName,      // Name of the car owner
    String carModel,       // Model of the car
    String carNo,          // Car registration number
    String ownerMobileNo,  // Mobile number (10 digits)
    String ownerAddress    // Address of the owner
) {
    /**
     * Compact Constructor - validates and normalizes data before object creation
     */
    public Parked_CarOwner_Details {
        // Validate owner name
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be empty");
        }
        
        // Validate car number
        if (carNo == null || carNo.isBlank()) {
            throw new IllegalArgumentException("Car number cannot be empty");
        }
        
        // Validate mobile number - must be exactly 10 digits
        if (ownerMobileNo == null || !ownerMobileNo.matches("\\d{10}")) {
            throw new IllegalArgumentException("Mobile number must be 10 digits");
        }
        
        // Normalize car number to uppercase for consistency
        carNo = carNo.toUpperCase().trim();
    }
    
    @Override
    public String toString() {
        return """
               ╔═══════════════════════════════════════════════╗
               ║          CAR OWNER DETAILS                    ║
               ╠═══════════════════════════════════════════════╣
               ║ Owner Name    : %-30s║
               ║ Car Model     : %-30s║
               ║ Car Number    : %-30s║
               ║ Mobile Number : %-30s║
               ║ Address       : %-30s║
               ╚═══════════════════════════════════════════════╝
               """.formatted(ownerName, carModel, carNo, ownerMobileNo, 
                           ownerAddress.length() > 30 ? ownerAddress.substring(0, 27) + "..." : ownerAddress);
    }
}