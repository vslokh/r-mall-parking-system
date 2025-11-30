// The ParkedCarOwnerDetails class represents the details of a car owner parked in the system.
// It uses Java Record feature, which is a special kind of class in Java introduced in Java 14 to model immutable data.

/**
 * ParkedCarOwnerDetails is a record which automatically generates the getter methods,
 * equals(), hashCode() and toString() methods based on the parameters defined in the record.
 */
public record ParkedCarOwnerDetails(
    // The name of the car owner
    String ownerName,
    // The contact number of the car owner, must be a valid phone number
    String contactNumber,
    // The license plate number of the parked car
    String licensePlateNumber
) {

    // Compact constructor to validate the input data during the instantiation of the record.
    public ParkedCarOwnerDetails {
        if (ownerName == null || ownerName.isBlank()) {
            throw new IllegalArgumentException("Owner name cannot be null or empty.");
        }
        if (contactNumber == null || !contactNumber.matches("\\d{10}")) {
            throw new IllegalArgumentException("Contact number must be a valid 10-digit number.");
        }
        if (licensePlateNumber == null || licensePlateNumber.isBlank()) {
            throw new IllegalArgumentException("License plate number cannot be null or empty.");
        }
    }

    /**
     * Custom toString method to output the details in a human-readable format.
     * Overrides the default implementation to provide a better representation.
     */
    @Override
    public String toString() {
        return "ParkedCarOwnerDetails {" +
               " ownerName='" + ownerName + '\'' +
               ", contactNumber='" + contactNumber + '\'' +
               ", licensePlateNumber='" + licensePlateNumber + '\'' +
               '}';
    }
}